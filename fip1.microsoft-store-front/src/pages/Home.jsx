import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import RatingSummary from '../components/RatingSummary';
import '../styles/Home.css';

const Home = ({ isLoggedIn }) => {
  const [games, setGames] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch('http://localhost:8080/api/applications')
      .then(res => {
        console.log("Statut HTTP :", res.status);
        console.log("Type de contenu :", res.headers.get("Content-Type"));
        if (!res.ok) {
          throw new Error(`Erreur HTTP : ${res.status}`);
        }
        return res.json();
      })
      .then(data => {
        console.log("Données reçues :", data);
        setGames(Array.isArray(data) ? data : []);
      })
      .catch(err => {
        console.error("Erreur attrapée :", err);
        setGames([]);
      });
  }, []);

  const GameCard = ({ game }) => (
    <div className="game-card">
      <Link to={`/game/${game.id_a}`}>
        <div className="game-image">
          <img 
            src={`data:image/png;base64,${game.logo}`} 
            alt={game.nom}
            onError={(e) => {
              e.target.src = "/api/placeholder/300/200";
            }}
          />
        </div>
        <div className="game-info">
          <h3>{game.nom}</h3>
          <p className="game-publisher">Version {game.version}</p>
          <RatingSummary gameId={game.id_a} compact={true} />
          <p className="game-price">
            {game.prix === 0 ? 'Gratuit' : `${game.prix} €`}
          </p>
        </div>
      </Link>
    </div>
  );

  return (
    <div className="home-page">
      <header className="hero-section">
        <div className="hero-content">
          <h1>Bienvenue sur Microsoft Store Clone</h1>
          <p>Découvrez les meilleures applications du store avec avis et commentaires</p>

          {!isLoggedIn && (
            <div style={{ marginTop: '20px' }}>
              <button 
                className="login-button" 
                onClick={() => navigate('/login')} 
                style={{ marginRight: '10px' }}
              >
                Se connecter
              </button>
              <button 
                className="login-button" 
                onClick={() => navigate('/signup')}
              >
                S'inscrire
              </button>
            </div>
          )}
        </div>
      </header>

      <section className="featured-section">
        <h2>Applications disponibles</h2>
        {games.length === 0 ? (
          <div style={{ 
            textAlign: 'center', 
            padding: '3rem', 
            color: '#666' 
          }}>
            <p>Aucune application disponible pour le moment.</p>
          </div>
        ) : (
          <div className="featured-games">
            {games.map(game => (
              <div key={game.id_a} className="featured-game-card">
                <Link to={`/game/${game.id_a}`}>
                  <div className="featured-game-image">
                    <img 
                      src={`data:image/png;base64,${game.logo}`} 
                      alt={game.nom}
                      onError={(e) => {
                        e.target.src = "/api/placeholder/300/200";
                      }}
                    />
                  </div>
                  <div className="featured-game-info">
                    <h3>{game.nom}</h3>
                    <p style={{ 
                      color: '#666', 
                      fontSize: '0.9rem',
                      margin: '0.5rem 0'
                    }}>
                      {game.description.length > 100 
                        ? `${game.description.substring(0, 100)}...`
                        : game.description
                      }
                    </p>
                    <RatingSummary gameId={game.id_a} compact={true} />
                    <div className="featured-game-details">
                      <span className="featured-game-publisher">v{game.version}</span>
                      <p className="featured-game-price">
                        {game.prix === 0 ? 'Gratuit' : `${game.prix} €`}
                      </p>
                    </div>
                  </div>
                </Link>
              </div>
            ))}
          </div>
        )}
      </section>

      {isLoggedIn && (
        <section className="user-section" style={{
          backgroundColor: '#f8f9fa',
          padding: '2rem',
          borderRadius: '8px',
          margin: '2rem 20px'
        }}>
          <h2>Pour vous</h2>
          <p>Explorez de nouvelles applications et partagez vos avis !</p>
          <div style={{ marginTop: '1rem' }}>
            <Link 
              to="/my-games" 
              className="browse-games-btn"
              style={{
                display: 'inline-block',
                backgroundColor: '#0078d7',
                color: 'white',
                textDecoration: 'none',
                padding: '0.75rem 1.5rem',
                borderRadius: '4px',
                marginRight: '1rem'
              }}
            >
              Mes jeux
            </Link>
            <Link 
              to="/profile" 
              className="browse-games-btn"
              style={{
                display: 'inline-block',
                backgroundColor: '#28a745',
                color: 'white',
                textDecoration: 'none',
                padding: '0.75rem 1.5rem',
                borderRadius: '4px'
              }}
            >
              Mon profil
            </Link>
          </div>
        </section>
      )}
    </div>
  );
};

export default Home;