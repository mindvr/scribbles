#!/usr/bin/env python3

def permutate(base, base_max, limit):
    if base_max[0] == -1:
        eff_limit = limit
    else:
        eff_limit = min(base[0] * base_max[0], limit)

    if len(base) == 1:
        return list(range(0, eff_limit, base[0]))

    vectors = []
    for x in range(0, eff_limit, base[0]):
        for v in permutate(base[1:], base_max[1:], limit - x):
            if isinstance(v, list):
                vectors.append([x] + v)
            elif isinstance(v, int):
                vectors.append([x, v])
            else:
                raise TypeError()
    return vectors



if __name__ == '__main__':
    base = [2, 3, 5]
    base_max = [-1, -1, -1]
    # base = [1, 1]
    # base_max = [3, -1]
    # base = [3]
    # base_max = [-1]

    assert len(base) == len(base_max)
    for v in permutate(base, base_max, 10):
        if sum(v) > 7:
            print(v, sum(v))
