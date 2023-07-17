require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc
LIC_FILES_CHKSUM = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
DEPENDS:append = " dtc-native u-boot-tools-native opensbi"

SRCREV="accbe020795de6850aca6b1a12fae4efff897763"
BRANCH="linux5.10-rzfive"

SRC_URI = "git://github.com/TinkerBoard/renesas-renesas-u-boot-cip.git;protocol=https;branch=${BRANCH} \
           file://BootLoaderHeader.bin \
           file://0001-spl-opensbi-convert-scratch-options-to-config.patch \
           file://0002-riscv-Update-PLMT-and-PLICSW-compatible-string.patch \
           file://0001-riscv-fix-build-with-binutils-2.38.patch \
           file://opensbi-options.cfg \
           file://boot.cfg \
           "

UBOOT_CONFIG_tinker-v-rzfive = "tinker_v_defconfig"

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

do_deploy:append() {
    install -m 755 ${B}/spl-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
    install -m 755 ${B}/fit-${MACHINE}.srec ${DEPLOY_DIR_IMAGE}
}

do_compile[depends] += "opensbi:do_deploy"

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
