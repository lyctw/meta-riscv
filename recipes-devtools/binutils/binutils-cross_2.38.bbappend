FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:tinker-v-rzfive += "file://0001-ld-emulparams-elf32lriscv-defs.sh-Adjust-TEXT_START_.patch"

