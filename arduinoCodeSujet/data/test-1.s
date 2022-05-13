#include <avr/io.h>
.include "data/m328Pdef.s"
__SP_H__ = 0x3e
__SP_L__ = 0x3d
__SREG__ = 0x3f
__tmp_reg__ = 0
__zero_reg__ = 1
;; -------------------------------------------------------
;; Section des variables enregistrées dans la mémoire SRAM
	.section .data

	;; Déclaration de la variable i sur 4 octets 
	.comm i, 4

;; -------------------------------------------------------
;; -------------------------------------------------------
;; Section du code enregistrées dans la mémoire FLASH
	.section .text

;; ajoute quelques fonctions de délai
.include "data/delay.s"

;; fonction appelée par le programme principal C
.global main_program
main_program:
	push r28
	push r29
	push __tmp_reg__
	in r28,__SP_L__
	in r29,__SP_H__

	;; Enregistrer la constante 11 dans 4 registres r24:r27
	ldi r24, 11
	ldi r25, 0
	ldi r26, 0
	ldi r27, 0
	;; Copier 4 octets à partir de r24 vers cible
	sts i, r24
	sts i+1, r25
	sts i+2, r26
	sts i+3, r27
	rjmp .L3:
.L4
.L3
	;; Copier la variable dans r28:r29
	lds r28, i
	lds r29, i+1
	lds r30, i+2
	lds r31, i+4
	;; Enregistrer la constante 15 dans 4 registres r32:r35
	ldi r32, 15
	ldi r33, 0
	ldi r34, 0
	ldi r35, 0
    ldi r18,1   ;; 1  dans r18
    sbiw r28,16 ;; on soustrait
    cpc r30,__zero_reg__	 ;; 0
    cpc r31,__zero_reg__	 ;; 0
	brsh .L4; - Branch if Not Equal
	pop __tmp_reg__
	pop r29
	pop r28
	ret
	.size	main_program, .-main_program
.end
;; -------------------------------------------------------
