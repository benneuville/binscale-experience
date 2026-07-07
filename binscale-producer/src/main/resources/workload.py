import pandas as pd
import matplotlib.pyplot as plt

# 1. Charger le CSV (remplace 'data.csv' par ton fichier)
data = pd.read_csv('2h.csv', header=None, names=['Temps', 'Flux'])

# 2. Créer la figure
plt.figure(figsize=(16, 6))

plt.fill_between(data['Temps'], data['Flux'],
                 color='#5C669F',
                 alpha=1)

plt.plot(data['Temps'], data['Flux'],
         marker='.',
         linestyle='-',
         color='#5C669F',
         linewidth=2,
         markersize=6)

# 3. Personnalisation
plt.title('Workload applied', fontsize=14, pad=20)
plt.xlabel('Time (s)', fontsize=12)
plt.ylabel('Workload', fontsize=12)
plt.grid(True, linestyle='--', alpha=0.3)  # Grille discrète

# 4. Sauvegarder en haute résolution
plt.tight_layout()
plt.savefig('flux_donnees.png', dpi=300, bbox_inches='tight')  # 300 DPI pour impression
plt.show()