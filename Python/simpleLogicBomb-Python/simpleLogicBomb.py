# ascameroal
# CPS 3498 Computer Security

# Poblem: Create a simple logic bomb, that generates random 8 digit numbers.
# When the number generated matches todays date, print out a message 5 times.

# ***NOTICE***
# It is quite possible that this could run forever
# since there are a total of 99,999,999 possible random 8 digit numbers.
# For better possibility of completion within our lifetime, it is
# suggested to change the upper and lower bounds closer to
# todays actual date in the format of ddmmyyyy
# to check what the date is, uncomment line 31


import datetime
import random


def main():
    print('please be patient, this may take awhile')
    print()
# LOWER and UPPER bounds
    lower = int('00000000')
    upper = int('99999999')
# get todays date from the system
    today = datetime.date.today()
# format into ddmmyyyy
    today_formatted = str(today.day) + str(today.month) + str(today.year)
# uncomment the following line to see what the current host/server percieves as the current date in format ddmmyyyy
    #print(today_formatted)
    if len(today_formatted) == 7:
        today_formatted = today_formatted.zfill(8)
# create the count
    count = 0
    while True:  # continue generating random numbers until a match is found
        count += 1
    # generate a random 8-digit number
        num = random.randrange(lower, upper)
        num1 = "%08i" % num  # add leading zeroes so it is a true 8 digit number
        if today_formatted == str(num1):  # check for match of date and random number
            for x in range(0,5):  # print message 5 times
                print('Today is ' + today_formatted)
                print('The count is: ' + str(count))
            break  # match has been found, en the loop

main()