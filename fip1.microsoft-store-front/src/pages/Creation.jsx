import React, { useState } from 'react';
import '../styles/Creation.css';

const Creation = () => {
  const [formData, setFormData] = useState({
    nom: '',
    description: '',
    version: '',
    note_de_mise_a_jour: '',
    prix: '',
    logo: '',
    gitUrl: '',
    gitUsername: '',
    gitPassword: '',
  });

  const handleChange = e => {
    const { name, value, files } = e.target;

    if (name === 'logo' && files.length > 0) {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64 = reader.result;
        const base64WithoutPrefix = base64.replace(/^data:image\/[a-zA-Z]+;base64,/, '');

        setFormData(prev => ({
          ...prev,
          logo: base64WithoutPrefix,
        }));
      };
      reader.readAsDataURL(files[0]);
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/applications', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          ...formData,
          prix: parseFloat(formData.prix),
        })
      });

      if (response.ok) {
        alert('Application ajoutée avec succès avec clonage !');
      } else {
        alert("Erreur lors de l'ajout.");
      }
    } catch (err) {
      console.error(err);
      alert("Erreur réseau");
    }
  };

  // return (
  //   <div className="creation-page">
  //     <h2>Ajouter une nouvelle application</h2>
  //     <form className="profile-form" onSubmit={handleSubmit}>
  //       <div className="form-group">
  //         <input type="text" name="nom" placeholder="Nom" onChange={handleChange} required />
  //         <textarea name="description" placeholder="Description" onChange={handleChange} required />
  //         <input type="text" name="version" placeholder="Version" onChange={handleChange} required />
  //         <textarea name="note_de_mise_a_jour" placeholder="Notes de mise à jour" onChange={handleChange} required />
  //         <input type="number" step="0.01" name="prix" placeholder="Prix" onChange={handleChange} required />
  //         <input type="file" name="logo" accept="image/*" onChange={handleChange} required />

  //         <hr />

  //         <h3>Informations Git (optionnel)</h3>
  //         <input type="text" name="gitUrl" placeholder="URL du dépôt Git" onChange={handleChange} />
  //         <input type="text" name="gitUsername" placeholder="Nom d'utilisateur Git (si privé)" onChange={handleChange} />
  //         <input type="password" name="gitPassword" placeholder="Mot de passe Git (si privé)" onChange={handleChange} />

  //         <button type="submit">Créer</button>
  //       </div>
  //     </form>
  //   </div>
  // );

  return (
    <div className="creation-container">
      <div className="creation-form-wrapper">
        <h2>Ajouter une nouvelle application</h2>
        <form className="creation-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <input type="text" name="nom" placeholder="Nom" onChange={handleChange} required />
            <input type="text" name="version" placeholder="Version" onChange={handleChange} required />
            <input type="number" step="0.01" name="prix" placeholder="Prix (€)" onChange={handleChange} required />
            <input type="file" name="logo" accept="image/*" onChange={handleChange} required />

            <textarea name="description" placeholder="Description" onChange={handleChange} required />
            <textarea name="note_de_mise_a_jour" placeholder="Notes de mise à jour" onChange={handleChange} required />

            <h3>Informations Git (optionnel)</h3>
            <input type="text" name="gitUrl" placeholder="URL du dépôt Git" onChange={handleChange} />
            <input type="text" name="gitUsername" placeholder="Nom d'utilisateur Git" onChange={handleChange} />
            <input type="password" name="gitPassword" placeholder="Mot de passe Git" onChange={handleChange} />

            <button type="submit">Créer</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Creation;
