# require recipes-kernel/linux/linux-renesas.inc
require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-tinker-v-rzfive:"
SUMMARY = "ASUS Tinker-V RZ/Five dev kernel recipe"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "v6.4_rzfive-yocto_v2"
SRCREV="1bc4110163f0d978dca44e6f292cc6b28ee9fbd0"
FORK ?= "lyctw"

SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-dts-Add-Tinker-V-dts.patch \
           file://set-mmap-min-addr.cfg \
           "

LINUX_VERSION ?= "6.3.x"
#LINUX_VERSION_EXTENSION:append:tinker-v-rzfive = "-rzfive"

KBUILD_DEFCONFIG = "rzf_defconfig"

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

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
