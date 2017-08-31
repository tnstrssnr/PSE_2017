import sys

expected = sys.argv[1]

result = sys.argv[2]

print("-------------------------------------------------")
print("")
print("")

with open(expected, 'r') as t1, open(result, 'r') as t2:
    fileone = t1.readlines()
    filetwo = t2.readlines()



print('Message Payload Check')

if (fileone == filetwo):
    print("Test passed")
else:
    print('Difference detected:')
    print("Expected:")
    print(fileone)
    print("")
    print("Actual:")
    print(filetwo)


print("")
print("")
print('done')
print("-------------------------------------------------")
