import { useEffect, useState } from 'react';

export default function useOfflineOwnedGames(userId, isOnline) {
  const ownedKey = `ownedGames_${userId}`;
  const pendingKey = `pendingActions_${userId}`;
  const [ownedGames, setOwnedGames] = useState([]);

  // Charger depuis localStorage au démarrage
  useEffect(() => {
    const localOwned = JSON.parse(localStorage.getItem(ownedKey)) || [];
    setOwnedGames(localOwned);
  }, [userId]);

  // Synchroniser les achats en attente à la reconnexion
  useEffect(() => {
    if (isOnline && userId) {
      const pending = JSON.parse(localStorage.getItem(pendingKey)) || [];

      const sync = async () => {
        const updatedPending = [];

        for (const action of pending) {
          if (action.type === 'BUY') {
            try {
              const res = await fetch('http://localhost:8080/api/possession', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id_u: userId, id_a: action.gameId })
              });

              if (!res.ok) {
                throw new Error('Erreur API');
              }
            } catch (err) {
              console.error("Échec de synchronisation:", err);
              updatedPending.push(action); // garde l’action en file
            }
          }
        }

        // Nettoyage
        localStorage.setItem(pendingKey, JSON.stringify(updatedPending));
      };

      sync();
    }
  }, [isOnline, userId]);

  // Ajoute un jeu à la liste locale + pending actions
  const buyGameOffline = (gameId) => {
    const updated = [...new Set([...ownedGames, gameId])];
    setOwnedGames(updated);
    localStorage.setItem(ownedKey, JSON.stringify(updated));

    const pending = JSON.parse(localStorage.getItem(pendingKey)) || [];
    pending.push({ type: 'BUY', gameId });
    localStorage.setItem(pendingKey, JSON.stringify(pending));
  };

  return { ownedGames, buyGameOffline };
}
