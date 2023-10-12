# require recipes-kernel/linux/linux-renesas.inc
require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-tinker-v-rzfive:"
SUMMARY = "ASUS Tinker-V RZ/Five dev kernel recipe"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-cmo-v12"
SRCREV="cf2ca46962c64fc0c18e91b003fff776f89e40b1"
FORK ?= "prabhakarlad"

SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-dts-Add-Tinker-V-dts.patch \
           file://v4-0001-riscv-errata-Rename-defines-for-Andes.patch \
           file://v4-0002-irqchip-riscv-intc-Allow-large-non-standard-hwirq.patch \
           file://v4-0003-irqchip-riscv-intc-Introduce-Andes-IRQ-chip.patch \
           file://v4-0004-riscv-dts-renesas-r9a07g043f-Update-compatible-st.patch \
           file://v4-0005-dt-bindings-riscv-Add-andestech-cpu-intc-to-inter.patch \
           file://v4-0006-perf-RISC-V-Eliminate-redundant-IRQ-enable-disabl.patch \
           file://v4-0007-riscv-errata-Add-Andes-PMU-errata.patch \
           file://v4-0008-riscv-andes-Support-symbolic-FW-and-HW-raw-events.patch \
           file://v4-0009-DROP-net-andes-ftmac100-Andes-support-for-Faraday.patch \
           file://v4-0010-TESTME-should-probe-SBI-PMU-first.patch \
           file://v4-0011-WIP.patch \
           file://v4-0012-check-Sscofpmf-on-AX65.patch \
           file://v4-0013-riscv_pmu_sbi-fdt-based-probe.patch \
           file://v4-0014-Revert-riscv_pmu_sbi-fdt-based-probe.patch \
           file://v4-0015-WIP-good.patch \
           file://v4-0016-FIXUP-cleanups.patch \
           file://v4-0017-DROP-Monitoring-scounterinten.patch \
           file://v4-0018-DROP-lower-freq-of-top-record.patch \
           file://set-mmap-min-addr.cfg \
           "


LINUX_VERSION ?= "6.5.0"
LINUX_VERSION_EXTENSION:append:tinker-v-rzfive = "-tinker-v"

KBUILD_DEFCONFIG = "rzf_defconfig"

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
