FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"
SRCREV:jh7100 = "c6a092cd80112529cb2e92e180767ff5341b22a3"
SRCREV:tinker-v-rzfive = "dc1c7db05e075e0910b93504370b50d064a51402"
#SRCREV:tinker-v-rzfive = "ec0559eb315bd11a5ef8865a8a8fa91ab6888250"

SRC_URI:jh7110 = "git://github.com/starfive-tech/opensbi;branch=JH7110_VisionFive2_devel;protocol=https"
SRC_URI:append:jh7110 = "\
	file://visionfive2-uboot-fit-image.its \
	"
SRC_URI:append:tinker-v-rzfive = " \
    file://0001-Makefile-Force-GNU-hashing.patch \
    file://0001-lib-serial-Add-compatible-string-for-renesas_scif.patch \
    file://0002-platform-rzfive-Add-platform-compatible-string.patch \
    file://0003-rzfive-enable-caches.patch \
    file://v8-0001-DROP-disable-SEMIHOSTING.patch \
    file://v8-0002-lib-ipi-Adjust-Andes-PLICSW-to-single-bit-per-har.patch \
    file://v8-0003-sbi-sbi_pmu-Improve-sbi_pmu_init-error-handling.patch \
    file://v8-0004-lib-sbi-Add-Xandespmu-in-hart-extensions.patch \
    file://v8-0005-sbi-sbi_pmu-Add-hw_counter_filter_mode-to-pmu-dev.patch \
    file://v8-0006-platform-include-andes45-Add-PMU-related-CSR-defi.patch \
    file://v8-0007-platform-generic-Introduce-pmu_init-platform-over.patch \
    file://v8-0008-platform-andes-Add-Andes-custom-PMU-support.patch \
    file://v8-0009-platform-andes-Enable-Andes-PMU-for-AE350.patch \
    file://v8-0010-platform-rzfive-Enable-Andes-PMU-for-RZ-Five.patch \
    file://v8-0011-lib-utils-fdt_fixup-Allow-preserving-PMU-properti.patch \
    file://v8-0012-platform-andes-Factor-out-is_andes-helper.patch \
    file://v8-0013-lib-utils-fdt_pmu-Make-the-fdt_pmu_evt_select-tab.patch \
    file://v8-0014-lib-utils-fdt_pmu-Do-not-iterate-over-the-fdt_pmu.patch \
    file://v8-0015-platform-andes-Add-Andes-default-PMU-mapping-supp.patch \
    file://v8-0016-docs-pmu-Add-Andes-PMU-node-example.patch \
    file://v8-0017-DROP-temp-fix.patch \
"

DEPENDS:append:jh7110 = " u-boot-tools-native dtc-native"
EXTRA_OEMAKE:append:jh7110 = " FW_TEXT_START=0x40000000"
EXTRA_OEMAKE:append:tinker-v-rzfive = " FW_TEXT_START=0x44000000 DEBUG=1"

do_deploy:append:jh7110() {
	install -m 0644 ${WORKDIR}/visionfive2-uboot-fit-image.its ${DEPLOYDIR}/visionfive2-uboot-fit-image.its
	cd ${DEPLOYDIR}
	mkimage -f visionfive2-uboot-fit-image.its -A riscv -O u-boot -T firmware visionfive2_fw_payload.img
}
