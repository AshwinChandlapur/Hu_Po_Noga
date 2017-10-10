package com.stickercamera.app.camera;

import com.stickercamera.app.camera.effect.FilterEffect;
import com.stickercamera.app.camera.util.GPUImageFilterTools;

import java.util.ArrayList;
import java.util.List;


public class EffectService {

    private static EffectService mInstance;

    public static EffectService getInst() {
        if (mInstance == null) {
            synchronized (EffectService.class) {
                if (mInstance == null)
                    mInstance = new EffectService();
            }
        }
        return mInstance;
    }

    private EffectService() {
    }

    public List<FilterEffect> getLocalFilters() {
        List<FilterEffect> filters = new ArrayList<FilterEffect>();
        filters.add(new FilterEffect("Normal", GPUImageFilterTools.FilterType.NORMAL, 0));

        filters.add(new FilterEffect("Amaro", GPUImageFilterTools.FilterType.ACV_AIMEI, 0));
        filters.add(new FilterEffect("Danlan", GPUImageFilterTools.FilterType.ACV_DANLAN, 0));
        filters.add(new FilterEffect("Aden", GPUImageFilterTools.FilterType.ACV_DANHUANG, 0));
        filters.add(new FilterEffect("Fugu", GPUImageFilterTools.FilterType.ACV_FUGU, 0));
        filters.add(new FilterEffect("Perpetua", GPUImageFilterTools.FilterType.ACV_GAOLENG, 0));
        filters.add(new FilterEffect("Ludwig", GPUImageFilterTools.FilterType.ACV_HUAIJIU, 0));
        filters.add(new FilterEffect("Valencia", GPUImageFilterTools.FilterType.ACV_JIAOPIAN, 0));
        filters.add(new FilterEffect("Keai", GPUImageFilterTools.FilterType.ACV_KEAI, 0));
        filters.add(new FilterEffect("Lomo", GPUImageFilterTools.FilterType.ACV_LOMO, 0));
        filters.add(new FilterEffect("Sieraa", GPUImageFilterTools.FilterType.ACV_MORENJIAQIANG, 0));
        filters.add(new FilterEffect("Mountain", GPUImageFilterTools.FilterType.ACV_NUANXIN, 0));
        filters.add(new FilterEffect("Hefe", GPUImageFilterTools.FilterType.ACV_QINGXIN, 0));
        filters.add(new FilterEffect("Rixi", GPUImageFilterTools.FilterType.ACV_RIXI, 0));
        filters.add(new FilterEffect("Mayfair", GPUImageFilterTools.FilterType.ACV_WENNUAN, 0));

        return filters;
    }

}
