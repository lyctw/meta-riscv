Tinker-V
========

Tinker-V is an industrial-grade, high-performance 64-bit RISC-V processor equipped
with the Renesas RZ/Five CPU tailored for IoT applications.

How to build
============

```
$ SHELL=/bin/bash kas-container build ./meta-riscv/tinker-v-rzfive.yml
```

The `kas-container` script can be obtained from
[kas repository](https://github.com/siemens/kas/blob/3.0.2/kas-container).

Build results
=============

- Flash_Writer_SCIF_TINKER_V.mot
- spl-tinker-v-rzfive.srec   // u-boot-spl
- fit-tinker-v-rzfive.srec   // u-boot.itb
- core-image-minimal-tinker-v-rzfive.rootfs.wic.gz


Flashing SD card
================

```
$ gunzip -c core-image-minimal-tinker-v-rzfive.rootfs.wic.gz  | sudo dd of=/dev/sdc bs=4M iflag=fullblock oflag=direct conv=fsync status=progress && sync
```

(Optional.) Flashing U-boot
===========================



=> env default -a
=> saveenv

Resources
=========

* [ASUS Tinker-V](https://tinker-board.asus.com/product/tinker-v.html)
* [TinkerBoard Github](https://github.com/TinkerBoard/TinkerBoard/wiki/Tinker-V)
* [Renesas RZ/Five General-purpose Microprocessors with RISC-V CPU Core (Andes AX45MP Single) (1.0 GHz) with 2ch Gigabit Ethernet](https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-mpus/rzfive-general-purpose-microprocessors-risc-v-cpu-core-andes-ax45mp-single-10-ghz-2ch-gigabit-ethernet)
