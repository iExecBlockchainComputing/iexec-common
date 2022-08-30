package com.iexec.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SgxUtils {
    public static final String SGX_CGROUP_PERMISSIONS = "rwm";
    public static final String LEGACY_SGX_DRIVER_PATH = "/sys/module/isgx/version";

}
