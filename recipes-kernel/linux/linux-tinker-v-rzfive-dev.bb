# require recipes-kernel/linux/linux-renesas.inc
require linux-mainline-common.inc
FILESEXTRAPATHS:prepend = "${FILE_DIRNAME}/linux-tinker-v-rzfive:"
SUMMARY = "ASUS Tinker-V RZ/Five dev kernel recipe"

LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KERNEL_VERSION_SANITY_SKIP="1"

BRANCH = "rzfive-cmo-v12"
SRCREV="cf2ca46962c64fc0c18e91b003fff776f89e40b1"
FORK ?= "prabhakarlad"
#BRANCH = "master"
#SRCREV="58720809f52779dc0f08e53e54b014209d13eebb"
#FORK ?= "torvalds"

SRC_URI = "git://github.com/${FORK}/linux.git;protocol=https;branch=${BRANCH} \
           file://0001-riscv-dts-Add-Tinker-V-dts.patch \
           file://v7-0001-DROP-riscv-dts-renesas-convert-isa-detection-to-n.patch \
           file://v7-0002-riscv-errata-Rename-defines-for-Andes.patch \
           file://v7-0003-irqchip-riscv-intc-Allow-large-non-standard-hwirq.patch \
           file://v7-0004-irqchip-riscv-intc-Introduce-Andes-IRQ-chip.patch \
           file://v7-0005-riscv-dts-renesas-r9a07g043f-Update-compatible-st.patch \
           file://v7-0006-dt-bindings-riscv-Add-andestech-cpu-intc-to-inter.patch \
           file://v7-0007-perf-RISC-V-Eliminate-redundant-IRQ-enable-disabl.patch \
           file://v7-0008-perf-RISC-V-Move-T-Head-PMU-to-CPU-feature-altern.patch \
           file://v7-0009-perf-RISC-V-Introduce-Andes-PMU-for-perf-event-sa.patch \
           file://v7-0010-riscv-dts-renesas-Add-Andes-PMU-extension.patch \
           file://v7-0011-riscv-andes-Support-symbolic-FW-and-HW-raw-events.patch \
           file://0001-L2C-irq-handler.patch \
           file://set-mmap-min-addr.cfg \
           "


LINUX_VERSION ?= "6.5.0"
#LINUX_VERSION ?= "6.6.0-rc6"
LINUX_VERSION_EXTENSION:append:tinker-v-rzfive = "-tinker-v"

KBUILD_DEFCONFIG = "rzf_defconfig"
#KBUILD_DEFCONFIG = "defconfig"

COMPATIBLE_MACHINE = "(tinker-v-rzfive)"
