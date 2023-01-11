SUMMARY = "RISC-V Open Source Supervisor Binary Interface (OpenSBI)"
DESCRIPTION = "OpenSBI aims to provide an open-source and extensible implementation of the RISC-V SBI specification for a platform specific firmware (M-mode) and a general purpose OS, hypervisor or bootloader (S-mode or HS-mode). OpenSBI implementation can be easily extended by RISC-V platform or System-on-Chip vendors to fit a particular hadware configuration."
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING.BSD;md5=42dd9555eb177f35150cf9aa240b61e5"

inherit autotools-brokensep deploy

PV = "1.2+git${SRCPV}"

# BRANCH = "work/OpenSBI-PMA"
# SRCREV="dc7f2b51269244bd9c942591af90d3a90b1fd3c6"
BRANCH = "master"
SRCREV = "dc1c7db05e075e0910b93504370b50d064a51402"

# User can set local git folder:
# SRC_URI = "git:///local/host/git/source/dir;branch=${BRANCH}"
# git://github.com/renesas-rz/rz_opensbi.git;protocol=https;branch=${BRANCH}
SRC_URI = "git://github.com/riscv-software-src/opensbi.git;protocol=https;branch=${BRANCH} \
           file://0001-lib-serial-Add-compatible-string-for-renesas_scif.patch \
           file://0002-platform-rzfive-Add-platform-compatible-string.patch \
           file://0003-rzfive-Force-mcache_ctl-value.patch \
           file://0001-print-mcache_ctl-at-final_init.patch \
           file://0002-DEBUG-enable-ggdb3.patch \
           "

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "PLATFORM=${RISCV_SBI_PLAT} DEBUG=1"

do_deploy () {
	install -m 755 ${S}/build/platform/${RISCV_SBI_PLAT}/firmware/fw_dynamic.* ${DEPLOYDIR}/
}

addtask deploy before do_build after do_install

FILES:${PN} += "/share/opensbi/*/${RISCV_SBI_PLAT}/firmware/fw_jump.*"
FILES:${PN} += "/share/opensbi/*/${RISCV_SBI_PLAT}/firmware/fw_payload.*"
FILES:${PN} += "/share/opensbi/*/${RISCV_SBI_PLAT}/firmware/fw_dynamic.*"

COMPATIBLE_HOST = "(riscv64|riscv32).*"
INHIBIT_PACKAGE_STRIP = "1"

SECURITY_CFLAGS = ""
