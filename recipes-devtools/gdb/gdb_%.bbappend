FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
EXTRA_OECONF:remove = " --disable-tui"
EXTRA_OECONF += " --enable-tui"
