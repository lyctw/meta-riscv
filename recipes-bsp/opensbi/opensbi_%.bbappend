FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"
SRCREV:jh7100 = "c6a092cd80112529cb2e92e180767ff5341b22a3"
#SRCREV:tinker-v-rzfive = "dc1c7db05e075e0910b93504370b50d064a51402"
SRCREV:tinker-v-rzfive = "ee1f83ca848d3639b808e52719bc7111f5a1be7c"

SRC_URI:jh7110 = "git://github.com/starfive-tech/opensbi;branch=JH7110_VisionFive2_devel;protocol=https"
SRC_URI:append:jh7110 = "\
	file://visionfive2-uboot-fit-image.its \
	"
SRC_URI:append:tinker-v-rzfive = " \
    file://0001-lib-serial-Add-compatible-string-for-renesas_scif.patch \
    file://0002-platform-rzfive-Add-platform-compatible-string.patch \
    file://0003-rzfive-enable-caches.patch \
    file://0001-sbi-sbi_pmu-Add-hw_counter_filter_mode-to-pmu-device.patch \
    file://0002-platform-include-andes45-Add-PMU-related-CSR-defines.patch \
    file://0003-platform-andes-Add-Andes-custom-PMU-support.patch \
    file://0004-platform-andes-Enable-Andes-PMU-for-AE350.patch \
    file://0005-platform-rzfive-Enable-Andes-PMU-for-RZ-Five.patch \
    file://0006-docs-pmu-Add-Andes-PMU-node-example.patch \
"

#    file://0001-DO-NOT-REVIEW-Add-fdt-pmu-debug-messsages.patch
#    file://0002-DO-NOT-REVIEW-print-counter-status-at-boot-time.patch


DEPENDS:append:jh7110 = " u-boot-tools-native dtc-native"
EXTRA_OEMAKE:append:jh7110 = " FW_TEXT_START=0x40000000"
EXTRA_OEMAKE:append:tinker-v-rzfive = " FW_TEXT_START=0x44000000"

do_deploy:append:jh7110() {
	install -m 0644 ${WORKDIR}/visionfive2-uboot-fit-image.its ${DEPLOYDIR}/visionfive2-uboot-fit-image.its
	cd ${DEPLOYDIR}
	mkimage -f visionfive2-uboot-fit-image.its -A riscv -O u-boot -T firmware visionfive2_fw_payload.img
}
