package jp.aoyagi.application.wekaapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ClassificationModel {
    Context mContext=null;
    //特徴量の数を代入
    static private final Attribute feature0= new Attribute("feature0");
    static private final Attribute feature1= new Attribute("feature1");
    static private final Attribute feature2= new Attribute("feature2");
    static private final Attribute feature3= new Attribute("feature3");


    //クラスを入れる
    private final static List<String> classList=new ArrayList<String>(){
        {
            add("shoot");
            add("pass");
            add("other");
//            add("heading");
//            add("running");
//            add("dribble");

        }
    };

    private final static ArrayList<Attribute> attributeList=new ArrayList<Attribute>(){
        {
            add(feature0);
            add(feature1);
            add(feature2);
            add(feature3);
            Attribute attributeClass=new Attribute("@@class@@",classList);
            add(attributeClass);
        }
    };

    private Classifier clf = null;

    public ClassificationModel(String model, Context context){
        mContext = context;
        getClassificationModel(model);
    }

    public String predict(final float[] features){
        Instances unknownSample = new Instances("UNKNOWNSAMPLE", attributeList,1);
        unknownSample.setClassIndex(unknownSample.numAttributes()-1);
        DenseInstance newInstance = new DenseInstance(unknownSample.numAttributes()){
            {
                setValue(feature0, features[0]);
                setValue(feature1, features[1]);
                setValue(feature2, features[2]);
                setValue(feature3, features[3]);
            }
        };
        String predictedLabel=null;
        newInstance.setDataset(unknownSample);
        double result = 0;
        try {
            result = clf.classifyInstance(newInstance);
            predictedLabel =classList.get(Double.valueOf(result).intValue());
        } catch (Exception e) {
            Log.d("result", "error2");
            e.printStackTrace();
        }
        return  predictedLabel;

    }

//assetsにあるモデルを読み込む関数
    public void getClassificationModel(String modelName){
        ObjectInputStream objectInputStream = null;
        AssetManager assetManager= mContext.getAssets();
        InputStream inputStream= null;
        try {
            inputStream = assetManager.open(modelName);

            objectInputStream = new ObjectInputStream(inputStream);

            clf = (Classifier) objectInputStream.readObject();

            objectInputStream.close();

            //こっちでもmodelを読み込める
            //  clf=(Classifier) weka.core.SerializationHelper.read(assetManager.open(modelName));
        } catch (IOException | ClassNotFoundException e) {
            Log.d("result", "error");
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("result", "error1");
            e.printStackTrace();
        }

    }
}
