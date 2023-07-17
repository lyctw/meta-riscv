# require recipes-kernel/linux/linux-renesas.inc
require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-tinker-v-rzfive:"
SUMMARY = "ASUS Tinker-V RZ/Five dev kernel recipe"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-cmo-v9"
SRCREV="1bc4110163f0d978dca44e6f292cc6b28ee9fbd0"
FORK ?= "prabhakarlad"

SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-dts-Add-Tinker-V-dts.patch \
           "

LINUX_VERSION ?= "6.4.0"
LINUX_VERSION_EXTENSION:append:tinker-v-rzfive = "-tinker-v"

KBUILD_DEFCONFIG = "rzf_defconfig"

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
