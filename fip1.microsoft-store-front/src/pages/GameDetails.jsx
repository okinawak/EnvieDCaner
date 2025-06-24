import React, { useState, useEffect } from 'react';
import { redirect, useParams } from 'react-router-dom';
import { Download, Check, ShoppingCart, Trash2 } from 'lucide-react'; // ajout de Trash2
import '../styles/GameDetails.css';
import '../styles/Comments.css';

const GameDetails = ({ games, isLoggedIn, user, isOnline }) => {
  const { id } = useParams();
  const [game, setGame] = useState(null);
  const [isOwned, setIsOwned] = useState(false);
  const [isInstalled, setIsInstalled] = useState(false);
  const [installing, setInstalling] = useState(false);
  const [logoSrc, setLogoSrc] = useState('');
  const [showModal, setShowModal] = useState(false);


  useEffect(() => {
    setIsOwned(false)
    const gameData = games.find(g => g.id_a === parseInt(id));
    if (gameData) {
      setGame(gameData);

      if (user && gameData) {
        checkOwnership(user.id, gameData.id_a);
      }

      if (gameData.logo) {
        setLogoSrc(`data:image/png;base64,${gameData.logo}`);
      }
    }
  }, [id, games, user]);

  const checkOwnership = async (userId, appId) => {
    try {
      const res = await fetch(`http://localhost:8080/api/possessions/${userId}/${appId}`);
      if (res.ok) {
        console.log(`L'utilisateur possède l'app ${appId}`);
        setIsOwned(true);
      } else {
        console.log(`L'utilisateur ne possède pas l'app ${appId}`);
        setIsOwned(false);
      }
    } catch (err) {
      console.error("Erreur lors de la vérification de possession :", err);
      setIsOwned(false);
    }
  };

  const handleBuy = async () => {
    try {
      const possession = {
        id: {
          id_u: user.id,
          id_a: game.id_a
        }
      };

      if(user.solde < game.prix){
        console.log("Pas assez de thunes ")
         setShowModal(true)
        
      }
      else {
        const response = await fetch('http://localhost:8080/api/possessions', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(possession),
        });

        if (!response.ok) throw new Error("Échec de l'achat");

        const data = await response.json();
        console.log("Achat enregistré :", data);
        setIsOwned(true);
        user.solde-=game.prix
      }


      const data = await response.json();
      console.log("Achat enregistré :", data);
      setIsOwned(true);

      // Lancer l'installation automatiquement après l'achat réussi
      await handleInstall();


    } catch (error) {
      console.error("Erreur lors de l'achat :", error);
    }
  };

  const handleUninstall = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/possessions/${user.id}/${game.id_a}`, {
        method: 'DELETE',
      });

      if (!response.ok) throw new Error("Échec de la désinstallation");

      setIsOwned(false);
      setIsInstalled(false);
      console.log("Jeu désinstallé.");
    } catch (err) {
      console.error("Erreur lors de la désinstallation :", err);
    }
  };

  const handleInstall = async () => {
    if (!isInstalled) {
      setInstalling(true);
      try {
        const response = await fetch(`http://localhost:8080/api/applications/${game.id_a}/install`, {
          method: 'POST',
        });

        if (!response.ok) throw new Error("Échec de l'installation");

        console.log("Application installée avec succès");
        setIsInstalled(true);
      } catch (err) {
        console.error("Erreur lors de l'installation :", err);
      } finally {
        setInstalling(false);
      }
    }
  };

  if (!game) return <div className="loading">Chargement...</div>;

  return (
    <div className="game-details-container">
      {showModal && (
        <h3 className='error-message'>Vous n'avez pas assez d'argent</h3>
      )}
      <div className="game-header"><h1>{game.title}</h1></div>

      {logoSrc && (
        <div className="game-main-image" style={{ textAlign: 'center', marginBottom: '2rem' }}>
          <img src={logoSrc} alt={`Logo de ${game.nom}`} style={{ width: '100%', maxWidth: '500px', borderRadius: '12px' }} />
        </div>
      )}

      <div className="game-sidebar" style={{ display: 'flex', justifyContent: 'center', marginBottom: '2rem' }}>
        <div className="game-actions" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '0.5rem', minWidth: '220px' }}>
          {isOwned ? (
            <>
              <button
                className={`install-button ${isInstalled ? 'installed' : ''} ${installing ? 'installing' : ''}`}
                onClick={handleInstall}
                disabled={isInstalled || installing}
                style={{ padding: '0.75rem 1.5rem', width: '100%' }}
              >
                {isInstalled ? <><Check size={16} /> Installé</> : installing ? <><Download size={16} /> Installation...</> : <><Download size={16} /> Mettre à jour</>}
              </button>

              <button
                className="uninstall-button"
                onClick={handleUninstall}
                style={{ backgroundColor: '#e74c3c', color: 'white', padding: '0.5rem 1.5rem', borderRadius: '8px', border: 'none', cursor: 'pointer', width: '100%' }}
              >
                <Trash2 size={16} /> Désinstaller
              </button>
            </>
          ) : (
            isLoggedIn && (
              <button
                className="buy-button"
                onClick={handleBuy}
                style={{ padding: '0.75rem 1.5rem', width: '100%' }}
              >
                <ShoppingCart size={16} /> Acheter - {game.prix === 0 ? 'Gratuit' : `${game.prix} €`}
              </button>
            )
          )}
          <div className="game-version" style={{ fontSize: '0.9rem', color: '#666' }}>
            Version : {game.version}
          </div>
        </div>
      </div>

      <div className="game-description">
        <h2>Description</h2>
        <p>{game.description}</p>
      </div>

      <div className="game-details">
        <h2>Note de Mise à Jour</h2>
        <p>{game.note_de_mise_a_jour}</p>
      </div>

      {/* Section des commentaires */}
      <CommentsSection 
        gameId={game.id_a} 
        user={user} 
        isLoggedIn={isLoggedIn} 
      />
    </div>
  );
};

export default GameDetails;