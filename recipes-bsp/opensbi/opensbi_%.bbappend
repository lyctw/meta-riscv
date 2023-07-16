# Mainline OpenSBI supports the C906 out of the box, but it needs a few tweaks
# and a new reset driver for the sunxi watchdog.
FILESEXTRAPATHS:prepend:nezha := "${THISDIR}/files:"
FILESEXTRAPATHS:prepend:tinker-v-rzfive := "${THISDIR}/files:"

SRC_URI:append:nezha = " \
    file://0001-lib-utils-fdt-Require-match-data-to-be-const.patch \
    file://0002-lib-utils-timer-Add-a-separate-compatible-for-the-D1.patch \
"

SRC_URI:append:tinker-v-rzfive = " \
    file://0001-lib-serial-Add-compatible-string-for-renesas_scif.patch \
    file://0002-platform-rzfive-Add-platform-compatible-string.patch \
    file://0003-rzfive-enable-caches.patch \
"

INSANE_SKIP:${PN}:nezha += "ldflags"
