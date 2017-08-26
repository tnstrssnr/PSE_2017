import sys

expected = sys.argv[1]

result = sys.argv[2]

with open(expected, 'r') as t1, open(result, 'r') as t2:
    fileone = t1.readlines()
    filetwo = t2.readlines()

print('Database Check')
print('Difference detected:')

counter = 0

for line in filetwo:
    if line not in fileone:
        print(line)
        counter = counter + 1

print("")
print("")
print('done')
print('Differences found: {}'.format(counter))
