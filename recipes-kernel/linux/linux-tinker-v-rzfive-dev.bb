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
           file://v2-0001-riscv-errata-Rename-defines-for-Andes.patch \
           file://v2-0002-irqchip-riscv-intc-Allow-large-non-standard-hwirq.patch \
           file://v2-0003-irqchip-riscv-intc-Add-Andes-irq-chip.patch \
           file://v2-0004-add-andes-cpu-intc.patch \
           file://v2-0005-Documentations-cpus.yaml-Add-andestech-cpu-intc.patch \
           file://v2-0006-perf-RISC-V-Eliminate-redundant-IRQ-enable-disabl.patch \
           file://v2-0007-riscv-errata-Add-Andes-PMU-errata.patch \
           file://0004-riscv-andes-Support-symbolic-FW-and-HW-raw-events.patch \
           file://0001-DROP-set-1KHz-freq-for-perf-record-top.patch \
           file://set-mmap-min-addr.cfg \
           "


LINUX_VERSION ?= "6.5.0"
LINUX_VERSION_EXTENSION:append:tinker-v-rzfive = "-tinker-v"

KBUILD_DEFCONFIG = "rzf_defconfig"

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
