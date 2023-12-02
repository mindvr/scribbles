#!/usr/bin/env python3

import sys


def permutate(bases, base_limits, limit):
    if base_limits[0] == -1:
        eff_limit = limit
    else:
        eff_limit = min(bases[0] * base_limits[0], limit)

    if len(bases) == 1:
        return list(range(0, eff_limit // bases[0] + 1))

    vectors = []
    for x in range(0, eff_limit // bases[0] + 1):
        for v in permutate(bases[1:], base_limits[1:], limit - x * bases[0]):
            if isinstance(v, list):
                vectors.append([x] + v)
            elif isinstance(v, int):
                vectors.append([x, v])
            else:
                raise TypeError()
    return vectors


def v_length(bases, vector):
    if isinstance(vector, int):
        return bases[0] * vector
    elif isinstance(vector, list):
        assert len(bases) == len(vector)
        x = [i * j for i, j in zip(bases, vector)]
        return sum(x)
    else:
        raise TypeError()


def ways_to_zero(bases, base_limits, exact, min_add, max_add):
    assert len(bases) == len(base_limits)
    limit = exact + max_add
    permutations = permutate(bases, base_limits, limit)
    permutations.reverse()

    def match_criterion(v):
        left = exact + min_add
        l = v_length(v, bases)
        return l == exact or left <= l < limit

    def add_total_and_diff(v):
        return (v_length(bases, v), v_length(bases, v) - exact) + tuple(v)

    return [add_total_and_diff(v) for v in permutations if match_criterion(v)]


def pars_ints(line):
    return list(map(int, line.replace(',', ' ').split()))


def print_csv(line):
    csv_line = ",".join(map(str, line))
    print(csv_line)


def main():
    base = pars_ints(sys.stdin.readline())
    base_max = pars_ints(sys.stdin.readline())
    s_exact, add_min, add_max = pars_ints(sys.stdin.readline())
    print_csv(['total', 'delta'] + base)
    for v in ways_to_zero(base, base_max, s_exact, add_min, add_max):
        print_csv(v)


if __name__ == '__main__':
    main()
