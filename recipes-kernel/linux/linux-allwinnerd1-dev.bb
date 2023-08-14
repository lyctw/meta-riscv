require recipes-kernel/linux/linux-mainline-common.inc

SUMMARY = "Allwinner D1 dev kernel recipe"
SRCREV_meta ?= "b7fc5d5cecaad5d97164bac8db5b5ee72f563bb0"
SRCREV_machine ?= "ca67838d84af4c9f85d06311c9e98e1adf46308f"
FORK ?= "smaeul"
BRANCH ?= "riscv/d1-wip"
KMETA = "kernel-meta"

# It is necessary to add to SRC_URI link to the 'yocto-kernel-cache' due to
# override of the original SRC_URI:
# "do_kernel_metadata: Check the SRC_URI for meta-data repositories or
# directories that may be missing"
SRC_URI = "git://github.com/${FORK}/linux.git;name=machine;protocol=https;branch=${BRANCH} \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=master;destsuffix=${KMETA} \
           file://0001-riscv-Rename-__switch_to_aux-fpu.patch \
           file://0002-riscv-Extending-cpufeature.c-to-detect-V-extension.patch \
           file://0003-riscv-Add-new-csr-defines-related-to-vector-extensio.patch \
           file://0004-riscv-Clear-vector-regfile-on-bootup.patch \
           file://0005-riscv-Disable-Vector-Instructions-for-kernel-itself.patch \
           file://0006-riscv-Introduce-Vector-enable-disable-helpers.patch \
           file://0007-riscv-Introduce-riscv_v_vsize-to-record-size-of-Vect.patch \
           file://0008-riscv-Introduce-struct-helpers-to-save-restore-per-t.patch \
           file://0009-riscv-Add-task-switch-support-for-vector.patch \
           file://0010-riscv-Allocate-user-s-vector-context-in-the-first-us.patch \
           file://0011-riscv-Add-ptrace-vector-support.patch \
           file://0012-riscv-signal-check-fp-reserved-words-unconditionally.patch \
           file://0013-riscv-signal-Add-sigcontext-save-restore-for-vector.patch \
           file://0014-riscv-signal-Report-signal-frame-size-to-userspace-v.patch \
           file://0015-riscv-signal-validate-altstack-to-reflect-Vector.patch \
           file://0016-riscv-prevent-stack-corruption-by-reserving-task_pt_.patch \
           file://0017-riscv-hwcap-change-ELF_HWCAP-to-a-function.patch \
           file://0018-riscv-Add-prctl-controls-for-userspace-vector-manage.patch \
           file://0019-riscv-Add-sysctl-to-set-the-default-vector-rule-for-.patch \
           file://0020-riscv-detect-assembler-support-for-.option-arch.patch \
           file://0021-riscv-Enable-Vector-code-to-be-built.patch \
           file://0022-riscv-Add-documentation-for-Vector.patch \
           file://0023-selftests-Test-RISC-V-Vector-prctl-interface.patch \
           "

SRC_URI:append:mangopi-mq-pro = " \
	       	   file://mangopi-mq-pro.cfg \
		   file://0001-Bluetooth-Add-new-quirk-for-broken-local-ext-feature.patch \
		   file://0002-Bluetooth-btrtl-add-support-for-the-RTL8723CS.patch \
		   file://0003-drivers-net-bluetooth-Enable-quirk-for-broken-local-.patch \
		 "
#
LINUX_VERSION ?= "6.1.0"
KERNEL_FEATURES += "features/cgroups/cgroups.cfg"
KERNEL_FEATURES += "ktypes/standard/standard.cfg"

KERNEL_VERSION_SANITY_SKIP="1"

COMPATIBLE_MACHINE = "(nezha-allwinner-d1|mangopi-mq-pro)"
LINUX_VERSION_EXTENSION:append:nezha-allwinner-d1 = "-nezha"
LINUX_VERSION_EXTENSION:append:mangopi-mq-pro = "-mangopi"


## Should be oveerriten in machine conf
KBUILD_DEFCONFIG ?= "allwinner_defconfig"
