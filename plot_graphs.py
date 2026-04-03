import csv
import os
import glob
import matplotlib.pyplot as plt

output_dir = 'app/csv_output'
graphs_dir = 'graphs'

if not os.path.exists(graphs_dir):
    os.makedirs(graphs_dir)

csv_files = glob.glob(os.path.join(output_dir, '*.csv'))

for file in csv_files:
    x_vals = []
    y_vals = []
    
    with open(file, 'r') as f:
        reader = csv.DictReader(f)
        for row in reader:
            try:
                x = float(row['X'])
                y = float(row['Result'])
                if abs(y) > 20: 
                    y = float('nan')
                x_vals.append(x)
                y_vals.append(y)
            except ValueError:
                continue
                
    title = os.path.basename(file).replace('.csv', '')
    
    plt.figure(figsize=(8, 5))
    plt.plot(x_vals, y_vals, label=title, color='blue', linewidth=2)
    plt.title(f'График функции: {title}')
    plt.xlabel('X')
    plt.ylabel('Result')
    plt.grid(True, linestyle='--', alpha=0.7)
    plt.legend()
    
    plt.axhline(0, color='black',linewidth=1)
    plt.axvline(0, color='black',linewidth=1)

    out_file = os.path.join(graphs_dir, f'{title}.png')
    plt.savefig(out_file, dpi=300)
    plt.close()
    print(f'График сохранен: {out_file}')
