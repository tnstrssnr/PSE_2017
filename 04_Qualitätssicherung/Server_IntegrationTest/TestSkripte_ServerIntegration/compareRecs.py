import sys

expected = sys.argv[1]

result = sys.argv[2]

print("-------------------------------------------------")
print("")
print("")

with open(expected, 'r') as t1, open(result, 'r') as t2:
    fileone = t1.readlines()
    filetwo = t2.readlines()

print('Message Receiver Check')
print('Differences detected:')

counter = 0

print("")
print("Additional sends:")
for line in filetwo:
    if line not in fileone:
        print(line)
        counter = counter + 1

print("")
print("Missing sends:")
for line in fileone:
    if line not in filetwo:
        print(line)
        counter = counter + 1

print("")
print("")
print('done')
print('Differences found: {}'.format(counter))
print("-------------------------------------------------")
