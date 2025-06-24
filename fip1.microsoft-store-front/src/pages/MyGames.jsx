import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Download, Check } from 'lucide-react';
import '../styles/MyGames.css';

const MyGames = ({ isLoggedIn, user }) => {
  const [games, setGames] = useState(() => {
    // Au démarrage, on récupère les jeux sauvegardés localement (offline)
    const savedGames = localStorage.getItem('myGames');
    return savedGames ? JSON.parse(savedGames) : [];
  });
  const [installedGames, setInstalledGames] = useState(() => {
    // Sauvegarde aussi les jeux installés localement
    const savedInstalled = localStorage.getItem('installedGames');
    return savedInstalled ? JSON.parse(savedInstalled) : [];
  });
  const [installing, setInstalling] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user && user.id) {
      fetch(`http://localhost:8080/api/possessions/${user.id}/applications`)
        .then(res => {
          if (!res.ok) throw new Error('Erreur lors du chargement des jeux');
          return res.json();
        })
        .then(data => {
          setGames(data);
          setLoading(false);
          // Sauvegarder localement pour accès offline
          localStorage.setItem('myGames', JSON.stringify(data));
        })
        .catch(err => {
          console.error(err);
          setLoading(false);
          // En cas d'erreur, on garde les jeux locaux déjà chargés
        });
    } else {
      // Pas connecté : on affiche la liste locale (déjà chargée dans useState)
      setLoading(false);
    }
  }, [user]);

  useEffect(() => {
    // Sauvegarde la liste des jeux installés dès qu'elle change
    localStorage.setItem('installedGames', JSON.stringify(installedGames));
  }, [installedGames]);

  const handleInstall = (gameId) => {
    setInstalling(gameId);
    setTimeout(() => {
      setInstalledGames(prev => [...prev, gameId]);
      setInstalling(null);
    }, 2000);
  };

  const isInstalled = (gameId) => installedGames.includes(gameId);

  if (loading) {
    return <div>Chargement de vos jeux...</div>;
  }

  return (
    <div className="my-games-page">
      <header className="my-games-header">
        <h1>Ma bibliothèque de jeux</h1>
        <p>Tous vos jeux en un seul endroit</p>
      </header>

      {games.length === 0 ? (
        <div className="no-games-message">
          <h2>Vous n'avez pas encore de jeux</h2>
          <p>Explorez le magasin pour découvrir des jeux incroyables</p>
          <Link to="/" className="browse-games-btn">Parcourir le magasin</Link>
        </div>
      ) : (
        <div className="owned-games">
          {games.map(game => (
            <div key={game.id_a} className="owned-game-card">
              <div className="owned-game-image">
                <img
                  src={game.logo ? `data:image/png;base64,${game.logo}` : '/api/placeholder/300/200'}
                  alt={game.nom}
                />
              </div>
              <div className="owned-game-info">
                <h3>{game.nom}</h3>
                <p className="owned-game-publisher">{game.version}</p>

                <div className="owned-game-actions">
                  <Link to={`/game/${game.id_a}`} className="view-details-btn">
                    Détails
                  </Link>

                  {isInstalled(game.id_a) ? (
                    <button className="installed-btn" disabled>
                      <Check size={16} /> Installé
                    </button>
                  ) : (
                    <button
                      className={`install-btn ${installing === game.id_a ? 'installing' : ''}`}
                      onClick={() => handleInstall(game.id_a)}
                      disabled={installing === game.id_a}
                    >
                      <Download size={16} />
                      {installing === game.id_a ? 'Installation...' : 'Installer'}
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyGames;
