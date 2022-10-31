require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
DEPENDS:append = " bc dtc-native opensbi-smarc-rzfive u-boot-tools-native"

SRCREV="ea6d1659b79402a6137e7cda9620bfe956801780"
BRANCH="v2021.12/rzf-smarc"

SRC_URI = " \
    git://github.com/renesas-rz/renesas-u-boot-cip.git;protocol=https;branch=${BRANCH} \
    file://0001-riscv-fix-build-with-binutils-2.38.patch \
    file://0002-spl-opensbi-convert-scratch-options-to-config.patch \
    file://0003-riscv-dts-Update-PLICSW-and-PLMT-for-OpenSBI.patch \
    file://0004-r9a07g043f.dtsi-resize-PLMT-region.patch \
    file://opensbi-options.cfg \
    file://tftp-mmc-boot.txt \
    file://uEnv-rzfive.txt \
    file://BootLoaderHeader.bin \
    "

# UBOOT_CONFIG_rzfive-dev = "rzf-dev_ddr4_defconfig"
UBOOT_CONFIG_smarc-rzfive = "smarc-rzf_defconfig"

do_compile:prepend() {
    export OPENSBI=${DEPLOY_DIR_IMAGE}/fw_dynamic.bin
}

do_compile:append() {
    oe_runmake -C ${S} O=${B} ${UBOOT_CONFIG}
    oe_runmake -C ${S} O=${B} -j4
}

do_compile:append() {
    cat ${WORKDIR}/BootLoaderHeader.bin  ${B}/spl/u-boot-spl.bin > ${B}/u-boot-spl_bp.bin
    objcopy -I binary -O srec --adjust-vma=0x00011E00 --srec-forceS3 ${B}/u-boot-spl_bp.bin ${B}/spl-${MACHINE}.srec
    objcopy -I binary -O srec --adjust-vma=0 --srec-forceS3 ${B}/u-boot.itb ${B}/fit-${MACHINE}.srec
}

# Overwrite this for your server
TFTP_SERVER_IP ?= "127.0.0.1"

do_configure:prepend() {
    if [ -f "${WORKDIR}/tftp-mmc-boot.txt" ]; then
        sed -i -e 's,@SERVERIP@,${TFTP_SERVER_IP},g' ${WORKDIR}/tftp-mmc-boot.txt
        mkimage -A riscv -O linux -T script -C none -n "U-Boot boot script" \
            -d ${WORKDIR}/tftp-mmc-boot.txt ${WORKDIR}/boot.scr.uimg
    fi
}

do_deploy:append() {
    if [ -f "${WORKDIR}/boot.scr.uimg" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 755 ${WORKDIR}/boot.scr.uimg ${DEPLOYDIR}
    fi
            
    if [ -f "${WORKDIR}/uEnv-rzfive.txt" ]; then
        install -d ${DEPLOY_DIR_IMAGE}
        install -m 644 ${WORKDIR}/uEnv-rzfive.txt ${DEPLOYDIR}/uEnv.txt
    fi

    install -m 755 ${B}/spl-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
    install -m 755 ${B}/fit-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
}

do_compile[depends] += "opensbi-smarc-rzfive:do_deploy"

COMPATIBLE_MACHINE = "(smarc-rzfive)"
