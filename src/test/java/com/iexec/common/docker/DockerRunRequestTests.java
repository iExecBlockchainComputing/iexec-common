package com.iexec.common.docker;

import com.github.dockerjava.api.model.Device;
import com.iexec.common.sgx.SgxDriverMode;
import com.iexec.common.utils.SgxUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class DockerRunRequestTests {

    // region LEGACY
    @Test
    void shouldAddLegacySgxDevice() {
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .sgxDriverMode(SgxDriverMode.LEGACY)
                .build();
        log.info("{}", request);
        assertThat(request.getDevices().get(0).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(0).getPathInContainer())
                .isEqualTo("/dev/isgx");
        assertThat(request.getDevices().get(0).getPathOnHost())
                .isEqualTo("/dev/isgx");
    }

    @Test
    void shouldAddLegacySgxDeviceThenAddDevices() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .sgxDriverMode(SgxDriverMode.LEGACY) // notice order here
                .devices(devices)
                .build();
        log.info("{}", request);
        assertThat(request.getDevices().get(0).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(0).getPathInContainer())
                .isEqualTo("/dev/isgx");
        assertThat(request.getDevices().get(0).getPathOnHost())
                .isEqualTo("/dev/isgx");
        assertThat(request.getDevices().get(1)).isEqualTo(device1);
        assertThat(request.getDevices().get(2)).isEqualTo(device2);
    }

    @Test
    void shouldAddDeviceThenAddLegacySgxDevice() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .devices(devices) // changed order here
                .sgxDriverMode(SgxDriverMode.LEGACY)
                .build();
        log.info("{}", request);
        assertThat(request.getDevices().get(0)).isEqualTo(device1);
        assertThat(request.getDevices().get(1)).isEqualTo(device2);
        assertThat(request.getDevices().get(2).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(2).getPathInContainer())
                .isEqualTo("/dev/isgx");
        assertThat(request.getDevices().get(2).getPathOnHost())
                .isEqualTo("/dev/isgx");
    }
    // endregion

    // region NATIVE
    @Test
    void shouldAddNativeSgxDevices() {
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .sgxDriverMode(SgxDriverMode.NATIVE)
                .build();
        log.info("{}", request);
        assertThat(request.getDevices().get(0).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);
        assertThat(request.getDevices().get(0).getPathInContainer())
                .isEqualTo("/dev/sgx/enclave");
        assertThat(request.getDevices().get(0).getPathOnHost())
                .isEqualTo("/dev/sgx/enclave");
        assertThat(request.getDevices().get(1).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);
        assertThat(request.getDevices().get(1).getPathInContainer())
                .isEqualTo("/dev/sgx/provision");
        assertThat(request.getDevices().get(1).getPathOnHost())
                .isEqualTo("/dev/sgx/provision");
    }
    // endregion

    // region No SGX device
    @Test
    void shouldAddDevices() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .devices(devices)
                .build();
        log.info("{}", request);
        assertThat(request.getDevices().get(0)).isEqualTo(device1);
        assertThat(request.getDevices().get(1)).isEqualTo(device2);
    }

    @Test
    void shouldReturnEmptyDeviceListWhenEmptyDeviceList() {
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .devices(new ArrayList<>())
                .build();
        assertThat(request.getDevices()).isNotNull();
        assertThat(request.getDevices()).isEmpty();
    }

    @Test
    void shouldReturnEmptyDeviceListWhenNoDevices() {
        DockerRunRequest request = DockerRunRequest.builder().build();
        assertThat(request.getSgxDriverMode()).isEqualTo(SgxDriverMode.NONE);
        assertThat(request.getDevices()).isNotNull();
        assertThat(request.getDevices()).isEmpty();
    }

    @Test
    void shouldAddNoDeviceSinceSgxDriverModeIsNone() {
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .sgxDriverMode(SgxDriverMode.NONE)
                .build();
        assertThat(request.getDevices()).isNotNull();
        assertThat(request.getDevices()).isEmpty();
    }
    // endregion
}
