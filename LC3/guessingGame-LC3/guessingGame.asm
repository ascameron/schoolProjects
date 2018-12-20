; ascameron
; CPS 2390 Computer Organization and Architecture

; Problem: create a guessing game where the user attempts to
; guess what the "answer" is.
; This version accepts/stores the "answer" from an original user

.ORIG   x3000
		LEA R0, INTRO		; output the intro
		PUTS
		ST R5, dANSWER	; store the decimal answer

ATTEMPT	AND R2, R2, #0
		LEA R0, INPUT_PROMPT
		PUTS
		ST R6, rTEMP		; save the attempt count
; loop to get input
INPUT		GETC 			; get input from keyboard
		OUT
; check for enter	
LD R7, checkENTER
		ADD R7, R0, R7
		BRz COMPARE
; check if a digit
		LD R7, upLIMIT
		ADD R7, R0, R7 		; check upper limit of 9
		BRp INVALID
		LD R7, lowLIMIT
		ADD R2, R0, R7 		; check lower limit of 0
		BRn INVALID
; input is a number, add to total
		ADD R1, R1, #0
		BRz SKIP 		; if first digit, skip multiplication
; shift place of previous digit
		AND R3, R3, #0
		ADD R3, R3, #10
		AND R7, R7, #0
SHIFT 		ADD R7, R7, R1;
		ADD R3, R3, #-1;
		BRp SHIFT
		ADD R1, R7, #0
SKIP		ADD R1, R1, R2 		; add the new digit to our recording number
		BR INPUT
; check if guess is correct
COMPARE	LD R6, rTEMP		; load the attempt count
		LD R5, dANSWER	; load the answer
		NOT R1, R1		; 2's comp on guess
		ADD R1, R1, #1
		ADD R1, R1, R5		; check if answer and guess are equal
		BRz CORRECT		; if zero, guessed correctly

		ADD R1, R1 #0
		BRn BIG		; guess too big

		LEA R0, TOOSMALL	; guess too small
		PUTS
		BR CONTINUE

BIG		LEA R0, TOOBIG	; too big message
		PUTS

CONTINUE	AND R1, R1, #0
		ADD R6, R6, #1
		ADD R2, R6, #-9		; check for 10th attempt
		BRz FAIL		; exit loop
		BR ATTEMPT		; guess again

INVALID	 LEA R0, INV
		PUTS
		BR CONTINUE
; guessed correctly
CORRECT	ADD R6, R6, #1
		LEA R0, WINNER	; output winning message
		PUTS
		LD R1, ADJUST		; change # of guesses to ascii
		ADD R0, R6, R1
		OUT
		LEA R0, WINNER2
		PUTS
		BR END		; go to end

; no more tries
FAIL		LEA R0, GAME_OVER	; output failure message
		PUTS
		LEA R0, sANSWER	; R0 is where we'll store the string

		AND R4, R4, #0 		; leading zeroes switch, 0 if no digits recorded
		LD R1, dANSWER	; R1 = number
		ADD R1, R1, #1 

		LD R3, nhund 		; hundreds place
		JSR CONVERT

		AND R3, R3, #0
		ADD R3, R3, #-10 	; tens place
		JSR CONVERT

		AND R3, R3, #0
		ADD R3, R3, #-1 	; ones place
		JSR CONVERT

		AND R1, R1, #0 		; and then plug the end of the string
		STR R1, R0, #0
		LEA R0, sANSWER	; load start of string, and output
		PUTS

; end the game
END		HALT

; converts each decimal digit of the answer into ascii
CONVERT	LD R2, sADJUST 	; ascii representation starting point
		ADD R5, R4, #0		; check for existing digit
		BRp LOOP 		; record zero, if its not the first digit
		ADD R5, R1, R3 		; R5 temp
		BRn ZERO 		; don’t record first zero
LOOP		ADD R2, R2, #1 		; increment ascii for each "division"
		ADD R1, R1, R3
BRp LOOP
		STR R2, R0, #0 		; store the ascii for each digit
		NOT R3, R3 		; return digit to positive
		ADD R3, R3, #1 		
		ADD R1, R1, R3 		
		ADD R0, R0, #1 		; increment string pointer
		ADD R4, R4, #1 		; now there are digits, set marker
ZERO		RET

; ascii codes to compare to
lowLIMIT 	.FILL #-48		; check lower limit
upLIMIT 	.FILL #-57		; check upper limit
checkENTER	.FILL #-10		; check for enter
sADJUST 	.FILL #47		; baseline for string to ascii
ADJUST	.FILL #48		; adjust the attempt count
nhund 		.FILL #-100		; for calculating 100’s place
rTEMP		.BLKW 1		; temp storage
dANSWER	.BLKW 1		; store decimal answer
sANSWER 	.BLKW 4		; string to store the answer

INTRO			.STRINGZ "Guessing Game: Guess a number between 1 and 999\n*You get 9 tries\n"
INPUT_PROMPT	.STRINGZ "Guess the number(hit enter when done): "
INV			.STRINGz "\nInvalid input.\n"
TOOBIG		.STRINGZ "Too big.\n"
TOOSMALL		.STRINGZ "Too small.\n"
WINNER		.STRINGZ "\nCorrect! You took "
WINNER2		.STRINGZ " guesses."
GAME_OVER		.STRINGZ "\nThe game is over. Correct answer is "

		.END
