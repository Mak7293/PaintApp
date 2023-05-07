package com.soft918.paintapp.di;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.room.Room;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.soft918.paintapp.R;
import com.soft918.paintapp.data.data_source.PaintUriDatabase;
import com.soft918.paintapp.data.repository.RepositoryImpl;
import com.soft918.paintapp.domain.repository.Repository;
import com.soft918.paintapp.domain.util.Constants;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public RequestManager provideGlideInstance(Application application) {
        return Glide.with(application)
                .setDefaultRequestOptions(
                    new RequestOptions()
                            .placeholder(R.drawable.ic_place_holder)
                            .error(R.drawable.ic_place_holder)
                );
    }
    @Provides
    @Singleton
    PaintUriDatabase provideLocationDatabase(Application app){
        return Room.databaseBuilder(
                app,
                PaintUriDatabase.class,
                PaintUriDatabase.DATABASE_NAME
        ).build();
    }
    @Provides
    @Singleton
    Repository provideRepositoryImpl(PaintUriDatabase db) {
        return new RepositoryImpl(
                db.paintUriDao()
        );
    }
    @Provides
    @Singleton
    SharedPreferences privateSharedPreferences(Application app) {
        return app.getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
    }

}
