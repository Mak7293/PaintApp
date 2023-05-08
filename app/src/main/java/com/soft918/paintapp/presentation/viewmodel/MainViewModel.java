package com.soft918.paintapp.presentation.viewmodel;


import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.soft918.paintapp.R;
import com.soft918.paintapp.domain.component.PaintView;
import com.soft918.paintapp.domain.event.Event;
import com.soft918.paintapp.domain.model.ColorSet;
import com.soft918.paintapp.domain.model.PaintUriEntity;
import com.soft918.paintapp.domain.repository.Repository;
import com.soft918.paintapp.domain.util.Constants;
import com.soft918.paintapp.domain.util.PencilEraserSize;
import com.soft918.paintapp.domain.util.PencilEraser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class MainViewModel extends ViewModel {

    private final Application application;
    private final Repository repository;
    public MutableLiveData<List<ColorSet>> colorList = new MutableLiveData<List<ColorSet>>();
    public MutableLiveData<Integer> selectedColor = new MutableLiveData<Integer>();
    public MutableLiveData<String> selectPencilEraser = new MutableLiveData<String>(PencilEraser.pencil.state);
    public MutableLiveData<String> pencilSize = new MutableLiveData<String>(PencilEraserSize.largeSize.size);
    public MutableLiveData<String> eraserSize = new MutableLiveData<String>(PencilEraserSize.largeSize.size);
    public ArrayList<PaintView.CustomPath> pathList = new ArrayList<>();
    public LiveData<List<PaintUriEntity>> getAllPaintPath;
    public MutableLiveData<String> current_theme = new MutableLiveData<>("");
    public int sampleImage = 0;
    public String drawnImage = "";
    public Uri importedImage;
    private SharedPreferences sharedPref;;
    private final ScheduledExecutorService backgroundExecutor = Executors.newSingleThreadScheduledExecutor();

    @Inject
    public MainViewModel(
            Application application,
            Repository repository,
            SharedPreferences sharedPref
    ){
        this.application = application;
        this.repository = repository;
        this.sharedPref = sharedPref;
        colorList.postValue(provideListOfColorSet());
        getAllPaintPath = repository.getAllPaintUri();
        getCurrentThemeFormDataStore();
    }
    private void getCurrentThemeFormDataStore()  {
        String themeId = sharedPref.getString(Constants.THEME_PREFERENCES_KEY,Constants.THEME_DEFAULT);
        current_theme.postValue(themeId);
    }
    public void onEvent(Event event){
        if (event instanceof Event.UpdateColorList){
            updateColorList(((Event.UpdateColorList) event).listId);
        } else if (event instanceof Event.SelectPencilOrEraser) {
            updateSelectedPencilEraser(((Event.SelectPencilOrEraser) event).state);
        } else if (event instanceof Event.changeSize) {
            updatePencilEraserSize(
                    ((Event.changeSize) event).size,
                    ((Event.changeSize) event).state
            );
        } else if(event instanceof Event.saveBitmapInDeviceStorage){
            saveBitmapInDeviceStorage(
                    ((Event.saveBitmapInDeviceStorage) event).bitmap
            );
        } else if (event instanceof Event.deletePaintInDb) {
            deletePaintPathInDb(
                    ((Event.deletePaintInDb) event).paintUriEntity
            );
        }
    }
    private void savePaintPathToDb(File file){
        Uri uri = FileProvider.getUriForFile(application,
                application.getApplicationContext().getPackageName() + ".fileprovider", file);
        try{
            repository.insertPaintUri(new PaintUriEntity(0, uri.toString(),file.getAbsolutePath()));
            Toast.makeText(application,
                    "نقاشی با موفقیت ذخیره شد.",Toast.LENGTH_LONG).show();
        }catch (SQLiteException e){
            Toast.makeText(application,
                    "خطا در ذخیره نقاشی، لطفا دوبار تلاش کنید.",Toast.LENGTH_LONG).show();
        }
    }
    private void deletePaintPathInDb(PaintUriEntity paintUriEntity){
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    repository.deletePaintUri(paintUriEntity);
                    deletePaintFile(paintUriEntity);
                    Toast.makeText(application,
                            "نقاشی با موفقیت حذف شد.",Toast.LENGTH_LONG).show();
                }catch (SQLiteException e){
                    Toast.makeText(application,
                            "خطا در حذف نقاشی، لطفا دوبار تلاش کنید.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void deletePaintFile(PaintUriEntity paintUriEntity){
        File file = new File(paintUriEntity.fileUri);
        if (file.exists()){
            boolean result = file.delete();
            if (result){
                Toast.makeText(application,"نقاشی با موفقیت حذف شد.",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(application,"خطا در حذف نقاشی، لطفا دوباره تلاش کنید.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveBitmapInDeviceStorage(Bitmap bitmap){
        final String[] result = new String[1];
        Executor mainExecutor = ContextCompat.getMainExecutor(application);
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if(bitmap != null){
                    try{
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,90,bytes);
                        File file = new File(application.getExternalCacheDir().getAbsoluteFile().toString() +
                                File.separator + "Paint" + System.currentTimeMillis()/1000 +
                                ".png" );
                        FileOutputStream fo = new FileOutputStream(file);
                        fo.write(bytes.toByteArray());
                        fo.close();
                        result[0] = file.getAbsolutePath();
                        savePaintPathToDb(file);
                        mainExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(!result[0].isEmpty()){
                                    Toast.makeText(application,"فایل نقاشی با موفقیت ذخیره شد:" + result[0],
                                            Toast.LENGTH_SHORT ).show();
                                }else{
                                    Toast.makeText(application,"خطا در ذخیره فایل، لطفا دوباره اقدام به ذخیره فایل نمایید.",
                                            Toast.LENGTH_SHORT ).show();
                                }
                            }
                        });
                    }catch(Exception e){
                        result[0] = "";
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void updatePencilEraserSize(String size, String state){
        if (size != null){
            if (state == PencilEraser.pencil.state){
                pencilSize.postValue(size);
            }else if(state == PencilEraser.eraser.state){
                eraserSize.postValue(size);
            }
        }
    }
    private void updateSelectedPencilEraser(String string){
        selectPencilEraser.postValue(string);
    }
    private  void updateColorList(int id){
        List<ColorSet> list = colorList.getValue();
        assert list != null;
        list.forEach((n) -> {
            n.isPicked = false;
        });
        if (!list.get(id).isPicked){
            list.get(id).isPicked = true;
        }
        colorList.postValue(list);
        selectedColor.postValue(list.get(id).color);
    }

    private List<ColorSet> provideListOfColorSet(){
        List<ColorSet> list = new ArrayList<ColorSet>();
        list.add(new ColorSet(ContextCompat.getColor(application, R.color.red_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.red_orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_orange_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.golden_yellow_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.peach_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.yellow_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.aqua_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.sky_blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.light_blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.blue_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.violet_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.brown_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.light_brown_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.mahogany_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.magenta_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.pink_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.jade_green_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.gray_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.tan_set),false));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.black),true));
        list.add(new ColorSet(ContextCompat.getColor(application,R.color.white),false));
        return list;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        backgroundExecutor.shutdown();
    }
}
