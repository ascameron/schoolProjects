;encryption
	.ORIG x3000
	
	LD R1, STORAGE		; load pointer for input
	LD R6, COUNT		; load counter for input
	LEA R0, RULES		; explain rules
	PUTS

	LEA R0, KEYIN		; get key from user
	PUTS
	GETC
	AND R0, R0, #15		; convert key to digit
	STI R0, KEY		; store key in mem[x3100]

	LEA R0, INPUT_		; ask user for input
	PUTS

INPUT	AND R2, R2, #0		; clear check register
	ADD R6, R6, #-1		; decrement counter #32
	GETC			; get input

	ADD R2, R0, #-10	; check for enter
	BRz EXIT		; if enter, go to adding EOT

	;Encrypt the input
	JSR ENCRYPT

	ADD R6, R6, #0		; check count
	BRz FULL		; if this is 32, go straight to storing
	
	;shift for 0dd, for even skip
	AND R2, R2, #0		; clear check register
	AND R2, R6, #1
	BRz EVEN

	;jump to odd subroutine
	JSR ODD			; shift to left 8 times
	ST R0, TEMP		; store in temp
	BR INPUT		; get next character

	;jump to even subroutine
EVEN	JSR EVENSUB		; combine terms and store
	ADD R1, R1, #1		; increment pointer
	BR INPUT		; check for input

FULL	LD R5, TEMP
	ADD R0, R0, R5		; combine terms
	STR R0, R1, #0
	LEA R0, CAPACITY	; output capacity reached
	PUTS
	BR END			; go to end

EXIT 	AND R0, R0, #0
	ADD R0, R0, #4		; set R0 to #4
	AND R2, R6, #1		; check even or odd
	BRz EV

	JSR ODD			; jump to odd subroutine
	;store in memory
	STR R0, R1, #0		; store in mem
	BR END			; go to end

	;jump to even subroutine
EV	JSR EVENSUB		; jump to even sub routine

END	AND R2, R2, #0
	AND R1, R1, #0
	
	HALT

;encrypt sub routine
ENCRYPT	ST R7, REGTEMP		; store return adress
	NOT R5, R0		; not the input
	LD R3, MASK		; mask original number with x00F0
	AND R0, R0, R3
	LD R3, MASK2		; mask altered lower 4 bits with x000F
	AND R5, R5, R3
	ADD R0, R0, R5		; combine terms
	LDI R7, KEY		; load encryption key
	ADD R0, R0, R7		; add encryption key
	OUT			; dysplay encrypted ASCII
	LD R7, REGTEMP		; load return adress
	RET

;odd sub routine	
ODD	ST R7, REGTEMP		; store return adress
	AND R7, R7, #0		; clear for count of #8
	ADD R7, R7, #8		; put #8 into R7
SHIFT	ADD R0, R0, R0		; shift left
	ADD R7, R7, #-1		; decrement counter
	BRp SHIFT
	LD R7, REGTEMP		; load return adress
	RET

;even sub routine
EVENSUB	ST R7, REGTEMP		; store return adress
	LD R5, TEMP		; load left ascii to R5
	ADD R0, R0, R5		; combine the 2 terms	
	STR R0, R1, #0		; store character
	LD R7, REGTEMP
	RET

TEMP	.BLKW 1
REGTEMP	.BLKW 1
COUNT	.FILL #32
KEY	.FILL x3100
MASK	.FILL x00F0
MASK2	.FILL x000F
STORAGE	.FILL x3120

RULES	.STRINGZ "Enter a phrase to Encrypt\nLimited to 32 characters including spaces.\n"
KEYIN	.STRINGZ "ENTER Key:"
INPUT_	.STRINGZ "\nEnter phrase (hit enter when done): "
CAPACITY	.STRINGZ "\n Max characters reached.\n"

	.END