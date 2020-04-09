package com.example.common.helper.okhttp3;

import com.example.common.utils.AppUtils;
import okhttp3.Cache;

import java.io.File;

public class HttpCache {
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.INSTANCE.getContext().getCacheDir().getAbsolutePath()
                + File.separator + "\"data/NetCache\"")
                , HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
