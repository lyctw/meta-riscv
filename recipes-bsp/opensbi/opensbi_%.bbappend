FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Support fdt drivers for AE350
SRCREV:ae350-ax45mp = "22f38ee6c658a660083aa45c4ec6c72f66a17260"
SRCREV:jh7100 = "c6a092cd80112529cb2e92e180767ff5341b22a3"
#SRCREV:tinker-v-rzfive = "dc1c7db05e075e0910b93504370b50d064a51402"
SRCREV:tinker-v-rzfive = "130e65dd9d44c11ef3a3048eb9a491d33e5f14b8"

SRC_URI:jh7110 = "git://github.com/starfive-tech/opensbi;branch=JH7110_VisionFive2_devel;protocol=https"
SRC_URI:append:jh7110 = "\
	file://visionfive2-uboot-fit-image.its \
	"
SRC_URI:append:tinker-v-rzfive = " \
    file://0001-lib-serial-Add-compatible-string-for-renesas_scif.patch \
    file://0002-platform-rzfive-Add-platform-compatible-string.patch \
    file://0003-rzfive-enable-caches.patch \
    file://v4-0001-sbi-sbi_pmu-Handle-errors-reported-by-sbi_platfor.patch \
    file://v4-0002-sbi-sbi_pmu-Add-hw_counter_filter_mode-to-pmu-dev.patch \
    file://v4-0003-platform-include-andes45-Add-PMU-related-CSR-defi.patch \
    file://v4-0004-platform-andes-Add-Andes-custom-PMU-support.patch \
    file://v4-0005-platform-andes-Enable-Andes-PMU-for-AE350.patch \
    file://v4-0006-platform-rzfive-Enable-Andes-PMU-for-RZ-Five.patch \
    file://v4-0007-docs-pmu-Add-Andes-PMU-node-example.patch \
    file://v4-0008-WIP.patch \
    file://v4-0009-Add-xandespmu-node-if-missing.patch \
    file://v4-0010-TESTING-don-t-grant-write.patch \
    file://v4-0011-DROP-disable-SEMIHOSTING.patch \
    file://v4-0012-DROP-Print-entries-at-boot-time.patch \
    file://v4-0013-Don-t-remove-pmu-node.patch \
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
