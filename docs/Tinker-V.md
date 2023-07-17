Tinker-V
========

[Tinker-V](https://tinker-board.asus.com/product/tinker-v.html) is an industrial-grade, high-performance 64-bit RISC-V
processor equipped with the [Renesas RZ/Five](https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-mpus/rzfive-general-purpose-microprocessors-risc-v-cpu-core-andes-ax45mp-single-10-ghz-2ch-gigabit-ethernet) CPU tailored for IoT
applications. This document will cover how to build SD card image and
flash the bootloader of Tinker-V.

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
- spl-tinker-v-rzfive.srec
- fit-tinker-v-rzfive.srec
- core-image-minimal-tinker-v-rzfive.rootfs.wic.gz


Flashing SD card
================

```
$ gunzip -c core-image-minimal-tinker-v-rzfive.rootfs.wic.gz | sudo dd of=/dev/sdc bs=4M iflag=fullblock oflag=direct conv=fsync status=progress && sync
```

The Linux image (Image-tinker-v-rzfive.bin) and the device tree
(r9a07g043f01-tinker-v.dtb) are stored in the first partiton.
The second partiton holds the root filesystem.

(Optional.) Flashing U-boot
===========================

1. Booting Flash Writer

Turn off the power and set the SW1 dipswitches to `on-on-on-on`.

```
    +-+-+-+-+
 ON |X|X|X|X|
OFF | | | | | SW1
    +-+-+-+-+
```

Turn on the power and you will see the messages below:

```
SCIF Download mode
 (C) Renesas Electronics Corp.
-- Load Program to System RAM ---------------
please send !
```

From your host PC, open a terminal and run the following command
(Assume that `/dev/ttyUSB0` is the serial port connecting to Tinker-V):

```
$ cat Flash_Writer_SCIF_TINKER_V.mot | sudo tee /dev/ttyUSB0
```

Flash Writer prompt will start automatically after the transfer is complete.

2. Writing U-Boot SPL

Enter `XLS2`, `11E00` and `00000` in the Flash Writer prompt.

```
>XLS2
===== Qspi writing of RZ/Five Board Command =============
Load Program to Spiflash
Writes to any of SPI address.
 Winbond : W25Q128JW
Program Top Address & Qspi Save Address
===== Please Input Program Top Address ============
  Please Input : H'11E00
 
===== Please Input Qspi Save Address ===
  Please Input : H'00000
Work RAM(H'50000000-H'53FFFFFF) Clear....
please send ! ('.' & CR stop load)
```

In another terminal, run the following command:

```
$ cat spl-tinker-v-rzfive.srec | sudo tee /dev/ttyUSB0
```

When the transfer is complete, enter `y` to update SPI flash.

3. Writing U-Boot ITB

The U-Boot ITB (fit-tinker-v-rzfive.srec) includes OpenSBI and
U-Boot proper.

Enter `XLS2`, `00000` and `20000` in the Flash Writer prompt.

```
>XLS2
===== Qspi writing of RZ/Five Board Command =============
Load Program to Spiflash
Writes to any of SPI address.
 Winbond : W25Q128JW
Program Top Address & Qspi Save Address 
===== Please Input Program Top Address ============
  Please Input : H'00000
 
===== Please Input Qspi Save Address ===
  Please Input : H'20000
Work RAM(H'50000000-H'53FFFFFF) Clear....
please send ! ('.' & CR stop load)
```

In another terminal, run the following command:

```
$ cat fit-tinker-v-rzfive.srec | sudo tee /dev/ttyUSB0
```

When the transfer is complete, enter `y` to update SPI flash.

After the U-Boot binaries are flashed, turn off the power and set the
SW1 dipswitches to `off-off-on-on`.

```
    +-+-+-+-+
 ON | | |X|X|
OFF |X|X| | | SW1
    +-+-+-+-+
```

Next, power on the board and you should see the updated U-Boot prompt. 

You may need to reset the environment variables:

```
=> env default -a
=> saveenv
```

Power on the board again and the U-Boot should automatically boot Linux from the SD card.

Resources
=========

* [ASUS Tinker-V](https://tinker-board.asus.com/product/tinker-v.html)
* [TinkerBoard Github](https://github.com/TinkerBoard/TinkerBoard/wiki/Tinker-V)
* [Renesas RZ/Five General-purpose Microprocessors with RISC-V CPU Core (Andes AX45MP Single) (1.0 GHz) with 2ch Gigabit Ethernet](https://www.renesas.com/us/en/products/microcontrollers-microprocessors/rz-mpus/rzfive-general-purpose-microprocessors-risc-v-cpu-core-andes-ax45mp-single-10-ghz-2ch-gigabit-ethernet)
