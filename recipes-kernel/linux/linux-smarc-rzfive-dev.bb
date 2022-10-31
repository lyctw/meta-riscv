# require recipes-kernel/linux/linux-renesas.inc
require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-smarc-rzfive:"
SUMMARY = "Renesas SMARC RZ/Five dev kernel recipe"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-5.10-cip1"
SRCREV="48de75691cc8f3c5fd75a784c7c42110752e268e"
FORK ?= "renesas-rz"

SRC_URI = " \
       git://github.com/${FORK}/rz_linux-cip.git;protocol=https;branch=${BRANCH} \
       file://0001-riscv-fix-build-with-binutils-2.38.patch \
       file://0002-dts-renesas-Add-plicsw-plmt-nodes-for-OpenSBI.patch \
       "

LINUX_VERSION ?= "5.10.x"
LINUX_VERSION_EXTENSION:append:ae350-ax45mp = "-rzfive"

KBUILD_DEFCONFIG = "renesas_defconfig"

do_deploy:append() {
	for dtbf in ${KERNEL_DEVICETREE}; do
		dtb=`normalize_dtb "$dtbf"`
		dtb_ext=${dtb##*.}
		dtb_base_name=`basename $dtb .$dtb_ext`
		for type in ${KERNEL_IMAGETYPE_FOR_MAKE}; do
			ln -sf $dtb_base_name-${KERNEL_DTB_NAME}.$dtb_ext $deployDir/$type-$dtb_base_name.$dtb_ext
		done
	done
}

COMPATIBLE_MACHINE = "(smarc-rzfive)"
