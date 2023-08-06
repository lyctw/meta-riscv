require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
#inherit uboot-extlinux-config

LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

INSANE_SKIP:${PN} += " ldflags"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
DEPENDS:append = " dtc-native u-boot-tools-native"

# https://git.beagleboard.org/beaglev-ahead/xuantie-ubuntu#u-boot-v202001
SRCREV="bbf3994802d4ce64a22ccf795bc6dfe4bb9205da"
BRANCH="beaglev-v2020.01-1.1.2"

SRC_URI = "git://github.com/beagleboard/beaglev-ahead-u-boot.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-fix-build-with-binutils-2.38.patch \
           file://0002-ENV_SETTINGS.patch \
           file://0003-fix-fix-bootargs.patch \
           file://0004-fix-ftbfs.patch \
           file://0005-feat-add-ci-build.patch \
           file://0006-fix-set-fixed-mac-addrs-1.patch \
           file://0007-lpi4a-use-distro_bootcmd.patch \
           file://0008-fix-ums.patch \
           file://0009-add-ums-in-light_lpi4a_defconfig.patch \
           file://0010-update.patch \
           file://0011-Prompt-TEST.patch \
           file://tftp-mmc-boot.txt \
           file://uEnv-beaglev-ahead.txt \
           file://fw_env.config \
           "

#UBOOT_CONFIG_beaglev-ahead-th1520 = "light_beagle_defconfig"

###### Overwrite this for your server
#####TFTP_SERVER_IP ?= "127.0.0.1"
######do_compile[depends] += "opensbi:do_deploy"
#####
#####do_configure:prepend() {
#####    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
#####    mkimage -O linux -T script -C none -n "U-Boot boot script" \
#####        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
#####}
#####
#####do_compile:prepend() {
#####    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/fw_dynamic.bin
#####    export OPENSBI=${B}/fw_dynamic.bin
#####}
#####
#####do_deploy:append() {
#####    install -m 644 ${B}/u-boot-sunxi-with-spl.bin ${DEPLOYDIR}
#####    install -m 644 ${WORKDIR}/uEnv-beaglev-ahead.txt ${DEPLOYDIR}/uEnv.txt
#####}

#LICENSE = "CLOSED"

do_configure:append() {
	mkdir ${B}/lib/
	cp ${S}/lib/sec_library ${B}/lib/ -rf
}

do_compile:append () {
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake envtools
}

SRC_URI += "file://fw_env.config"

do_install:append() {
    install -d ${D}${sysconfdir}
    install -d ${D}${bindir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}
    install -m 0755 ${B}/tools/env/fw_printenv ${D}${bindir}
    ln -rsf ${D}${bindir}/fw_printenv ${D}${bindir}/fw_setenv
}

FILES:${PN} += " ${bindir} "
INSANE_SKIP:${PN} += "installed-vs-shipped"

COMPATIBLE_MACHINE = "(beaglev-ahead-th1520)"
