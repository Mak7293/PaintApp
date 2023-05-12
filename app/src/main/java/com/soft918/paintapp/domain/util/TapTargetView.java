package com.soft918.paintapp.domain.util;

import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.soft918.paintapp.R;
import com.soft918.paintapp.presentation.ui.MainActivity;

import java.util.List;

public class TapTargetView {
    public static void PaintFragmentTapTargetView(FragmentActivity activity, List<View> view){
        new TapTargetSequence(activity)
                .targets(
                        TapTarget.forView(view.get(0), "انتخاب مداد رنگی",
                                        "با کلیک کردن روی یکی از مداد ها، مداد رنگی مربوطه انتخاب می شود. ")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(1), "انتخاب مداد",
                                        "با کلیک کردن بر روی این قسمت مداد به عنوان ابزار نقاشی انتخاب میشود.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(2), "تغییر سایز مداد",
                                        "با کلیک کردن روی دکمه + سایز مداد افزایش و با کلیک کردن روی دکمه - سایز مداد کاهش می یابد.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(3), "انتخاب پاک کن",
                                        "با کلیک کردن بر روی این قسمت پاک کن به عنوان ابزار نقاشی انتخاب میشود.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(4), "تغییر سایز پاک کن",
                                        "با کلیک کردن روی دکمه + سایز پاک کن افزایش و با کلیک کردن روی دکمه - سایز پاک کن کاهش می یابد.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(5), "بازگرداندن تغییرات",
                                        "با هر بار کلیک بر روی این دکمه، تغییرات نقاشی کشیده شده یک مرحله به عقب باز می گردد.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(activity, "معرفی ابزار های نقاشی به پایان رسید.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        // Perform action for the current target
                    }
                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                }).start();
    }
    public static void SampleDesignFragmentTapTargetView(FragmentActivity activity, View view){
        new TapTargetSequence(activity)
                .targets(
                        TapTarget.forView(view, "انتخاب تصویر پس زمینه",
                                        " می توانید از تصاویر پیشفرض این قسمت به عنوان تصویر کمکی برای" +
                                                " نقاشی استفاده کنید و در انتها این تصویر کمکی را پاک کنید.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //NO--OP//
                    }
                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        //NO--OP//
                    }
                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        //NO--OP//
                    }
                }).start();
    }
    public static void DrawnDrawingFragmentTapTargetView(FragmentActivity activity, List<View> view){
        new TapTargetSequence(activity)
                .targets(
                        TapTarget.forView(view.get(0), "اضافه کردن نقاشی کشیده شده به صفحه نقاشی",
                                        "می توانید با کلیک کردن بر روی تصاویر نقاشی های کشیده" +
                                                " شده، آن ها را به صفحه نقاشی اضافه کرده " +
                                                "و نقاشی های قبلی را کامل تر کرده" +
                                                " و دوباره اقدام به ذخیره سازی نقاشی نمایید.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(75)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(1), "حذف کردن نقاشی های کشیده شده",
                                        "حذف نقاشی به صورت دائمی از حافظه داخلی.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color),
                        TapTarget.forView(view.get(2), "به اشتراک گذاری نقاشی های کشیده شده",
                                        "می توانید نقاشی های کشیده شده را در شبکه های اجتماعی منتشر کنید.")
                                .dimColor(android.R.color.white)
                                .outerCircleColor(R.color.num2_pallet_three)
                                .targetCircleColor(R.color.num2_pallet_two)
                                .transparentTarget(true)
                                .targetRadius(45)
                                .titleTextColor(R.color.black_text_color)
                                .descriptionTextColor(R.color.black_text_color)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //NO--OP//
                    }
                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        //NO--OP//
                    }
                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        //NO--OP//
                    }
                }).start();
    }
}
