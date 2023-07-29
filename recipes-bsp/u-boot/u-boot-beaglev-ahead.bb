require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
DEPENDS:append = " dtc-native u-boot-tools-native opensbi"

# https://git.beagleboard.org/beaglev-ahead/xuantie-ubuntu#u-boot-v202001
SRCREV="bbf3994802d4ce64a22ccf795bc6dfe4bb9205da"
BRANCH="beaglev-v2020.01-1.1.2"

SRC_URI = "git@github.com:beagleboard/beaglev-ahead-u-boot.git;protocol=https;branch=${BRANCH} \
           file://tftp-mmc-boot.txt \
           file://uEnv-beaglev-ahead.txt \
           "

UBOOT_CONFIG_beaglev-ahead-th1520 = "light_beagle_defconfig"

do_configure:prepend() {
    sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
    mkimage -O linux -T script -C none -n "U-Boot boot script" \
        -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_compile:prepend() {
    cp ${DEPLOY_DIR_IMAGE}/fw_dynamic.bin ${B}/fw_dynamic.bin
    export OPENSBI=${B}/fw_dynamic.bin
}

do_deploy:append() {
    install -m 644 ${B}/u-boot-sunxi-with-spl.bin ${DEPLOYDIR}
    install -m 644 ${WORKDIR}/uEnv-beaglev-ahead.txt ${DEPLOYDIR}/uEnv.txt
}

do_compile[depends] += "opensbi:do_deploy"

COMPATIBLE_MACHINE = "(beaglev-ahead-th1520)"
