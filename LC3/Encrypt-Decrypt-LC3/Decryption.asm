;decryption

	.ORIG x4000

	AND R3, R3, #0
	LEA R0, DECRPYTKEY	;get key
	PUTS

;get the key from user
	GETC
	AND R0, R0, #15		;turn key into digit

	NOT R0, R0		;perform 2's comp
	ADD R0, R0, #1
	STI R0, KEY2
	
	LEA R0, STATEMENT	;output message
	PUTS

	LD R1, DATA		;load address of file

;start decryption process

DECRYPT	LD R0, CHECK
	ADD R0, R0, R1
	BRz	DONE

	LDR R0, R1, #0		;put char into R0

;seperate the 2 chars
	LD R3, RIGHT
	AND R3, R0, R3		; put right 8 bits in R3
	LD R4, LEFT
	AND R4, R0, R4		; put left 8 bits in R4

;move the leftmost char to the right
	;shift R4 to the right 8 bits
	AND R7, R7, #0
	ADD R7, R7, #8			; count for shift
SHIFT	
	ADD R4, R4, #0			;check if - or +

	BRn NEG
	ADD R4, R4, R4			; shift left
	
	BR DECREMENT
	
NEG	ADD R4, R4, R4
	ADD R4, R4, #1

DECREMENT	ADD R7, R7, #-1		;decrement counter
	BRp SHIFT			; start again

;R3 has right number, R4 has left number
	ST R3, TEMP2
	AND R0, R0, #0
	ADD R0, R0, R4			; put left number in R0

	AND R6, R6, #0
	ADD R6, R6, #-4		;EOT
	ADD R6, R6, R0
	BRz DONE		;if EOT, done

RESTORE	LDI R2, KEY2
	
	ADD R0, R0, R2		;combine key and cipher
	NOT R5, R0	
	LD R3, MASK2_	
	AND R5, R5, R3	;mask with x000F
	LD R3, MASK_
	AND R0, R0, R3	;mask with x00F0
	ADD R0, R0, R5	;combine numbers
	OUT

	LD R0, TEMP2
	AND R6, R6, #0
	ADD R6, R6, #-4		;EOT
	ADD R6, R6, R0
	BRz DONE		;if EOT, done

	ADD R0, R0, R2		;combine key and cipher
	NOT R5, R0	
	LD R3, MASK2_	
	AND R5, R5, R3	;mask with x000F
	LD R3, MASK_
	AND R0, R0, R3	;mask with x00F0
	ADD R0, R0, R5
	OUT

	ADD R1, R1, #1		;increment pointer
	BR DECRYPT	;check for EOT

	DONE

	HALT
	
CHECK	.FILL xCED1
RIGHT	.FILL x00FF
LEFT	.FILL xFF00
KEY2	.FILL x3100
MASK_	.FILL x00F0
MASK2_	.FILL x000F
DATA	.FILL x3120
DATA2	.FILL x3110
TEMP2	.BLKW 1

STATEMENT	.STRINGZ "\nThe decrypted cipher is: "
DECRPYTKEY	.STRINGZ "\n\nENTER DECRYPTION KEY: "

	.END