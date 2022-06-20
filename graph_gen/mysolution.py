import random


def random_weight():
    number = random.randint(-10, 30)
    if number == 0:
        return 1
    return number


def generate_graph(vertices_num, density):
    writable_data = []
    for v in range(vertices_num - 1):
        writable_data.append([v, v+1, random_weight()])
        ends = list(range(v+2, vertices_num))
        options_num = len(ends)

        for times in range(int(options_num * density)):
            end = ends.pop(random.randrange(len(ends)))
            writable_data.append([v, end, random_weight()])

    return sorted(writable_data), vertices_num


def write_graph(file_name, graph, v_num):
    with open(file_name, 'w') as f:
        line = ' '.join(list(map(lambda x: str(x), [v_num, len(graph)]))) + '\n'
        f.write(line)

        for edge in graph:
            line = ' '.join(list(map(lambda x: str(x), edge))) + '\n'
            f.write(line)


graph, v_num = generate_graph(2500, 0.8)
write_graph('graph_v2500_p08.txt', graph, v_num)
