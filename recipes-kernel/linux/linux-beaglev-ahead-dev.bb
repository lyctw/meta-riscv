require linux-mainline-common.inc
SUMMARY = "BeagleV-Ahead (TH1520) dev kernel recipe"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP = "1"

SRCREV ?= "bc0be96429149c7846133d1daa50ccba8e1458d2"
BRANCH:beaglev-ahead-th1520 = "v6.5-rc2_bva"
FORK ?= "lyctw"
SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
          "

LINUX_VERSION ?= "v6.5-rc2"
LINUX_VERSION_EXTENSION:append:beaglev-ahead-th1520 = "-beaglev-ahead"

KBUILD_DEFCONFIG:beaglev-ahead-th1520 = "defconfig"

COMPATIBLE_MACHINE = "(beaglev-ahead-th1520)"
