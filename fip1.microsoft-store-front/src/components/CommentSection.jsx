import React, { useState, useEffect } from 'react';
import { Star, Edit2, Trash2, Send } from 'lucide-react';
import { useNotification } from './Notification';

const CommentsSection = ({ gameId, user, isLoggedIn }) => {
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState({ contenu: '', note: 5 });
  const [editingComment, setEditingComment] = useState(null);
  const [loading, setLoading] = useState(true);
  const { addNotification, NotificationContainer } = useNotification();

  useEffect(() => {
    loadComments();
  }, [gameId]);

  const loadComments = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/application/${gameId}`);
      if (response.ok) {
        const data = await response.json();
        setComments(data);
      }
    } catch (error) {
      console.error('Erreur lors du chargement des commentaires:', error);
      addNotification('Erreur lors du chargement des commentaires', 'error');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmitComment = async (e) => {
    e.preventDefault();
    if (!isLoggedIn) return;

    try {
      const response = await fetch('http://localhost:8080/api/commentaires', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          contenu: newComment.contenu,
          note: newComment.note,
          id_u: user.id,
          id_a: gameId
        })
      });

      if (response.ok) {
        setNewComment({ contenu: '', note: 5 });
        loadComments();
        addNotification('Commentaire ajouté avec succès !', 'success');
      } else {
        addNotification('Erreur lors de l\'ajout du commentaire', 'error');
      }
    } catch (error) {
      console.error('Erreur:', error);
      addNotification('Erreur réseau', 'error');
    }
  };

  const handleEditComment = async (commentId, updatedContent, updatedRating) => {
    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/${commentId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          contenu: updatedContent,
          note: updatedRating,
          id_u: user.id
        })
      });

      if (response.ok) {
        setEditingComment(null);
        loadComments();
        addNotification('Commentaire modifié avec succès !', 'success');
      } else {
        addNotification('Erreur lors de la modification', 'error');
      }
    } catch (error) {
      console.error('Erreur:', error);
      addNotification('Erreur réseau', 'error');
    }
  };

  const handleDeleteComment = async (commentId) => {
    if (!window.confirm('Êtes-vous sûr de vouloir supprimer ce commentaire ?')) return;

    try {
      const response = await fetch(`http://localhost:8080/api/commentaires/${commentId}?userId=${user.id}`, {
        method: 'DELETE'
      });

      if (response.ok) {
        loadComments();
        addNotification('Commentaire supprimé avec succès !', 'success');
      } else {
        addNotification('Erreur lors de la suppression', 'error');
      }
    } catch (error) {
      console.error('Erreur:', error);
      addNotification('Erreur réseau', 'error');
    }
  };

  const renderStars = (rating, interactive = false, onRatingChange = null) => {
    return (
      <div className="stars" style={{ display: 'flex', gap: '2px' }}>
        {[1, 2, 3, 4, 5].map((star) => (
          <Star
            key={star}
            size={16}
            fill={star <= rating ? '#ffc107' : 'none'}
            stroke={star <= rating ? '#ffc107' : '#ccc'}
            style={{ cursor: interactive ? 'pointer' : 'default' }}
            onClick={interactive ? () => onRatingChange(star) : undefined}
          />
        ))}
      </div>
    );
  };

  const averageRating = comments.length > 0 
    ? (comments.reduce((sum, comment) => sum + comment.note, 0) / comments.length).toFixed(1)
    : 0;

  if (loading) return <div>Chargement des commentaires...</div>;

  return (
    <div className="comments-section" style={{ marginTop: '2rem' }}>
      <NotificationContainer />
      <h2>Commentaires et Notes</h2>
      
      {comments.length > 0 && (
        <div className="rating-summary" style={{ 
          backgroundColor: '#f8f9fa', 
          padding: '1rem', 
          borderRadius: '8px', 
          marginBottom: '1.5rem',
          display: 'flex',
          alignItems: 'center',
          gap: '1rem'
        }}>
          <div className="average-rating">
            <span style={{ fontSize: '2rem', fontWeight: 'bold' }}>{averageRating}</span>
            {renderStars(Math.round(averageRating))}
            <span style={{ color: '#666', fontSize: '0.9rem' }}>
              ({comments.length} avis)
            </span>
          </div>
        </div>
      )}

      {isLoggedIn && (
        <form onSubmit={handleSubmitComment} className="add-comment-form" style={{
          backgroundColor: '#f8f9fa',
          padding: '1.5rem',
          borderRadius: '8px',
          marginBottom: '2rem'
        }}>
          <h3>Ajouter un commentaire</h3>
          
          <div style={{ marginBottom: '1rem' }}>
            <label>Note :</label>
            <div style={{ marginTop: '0.5rem' }}>
              {renderStars(newComment.note, true, (rating) => 
                setNewComment(prev => ({ ...prev, note: rating }))
              )}
            </div>
          </div>

          <div style={{ marginBottom: '1rem' }}>
            <label>Commentaire :</label>
            <textarea
              value={newComment.contenu}
              onChange={(e) => setNewComment(prev => ({ ...prev, contenu: e.target.value }))}
              placeholder="Partagez votre expérience avec cette application..."
              required
              rows="4"
              style={{
                width: '100%',
                marginTop: '0.5rem',
                padding: '0.75rem',
                borderRadius: '4px',
                border: '1px solid #ddd',
                resize: 'vertical'
              }}
            />
          </div>

          <button type="submit" style={{
            backgroundColor: '#0078d7',
            color: 'white',
            border: 'none',
            padding: '0.75rem 1.5rem',
            borderRadius: '4px',
            cursor: 'pointer',
            display: 'flex',
            alignItems: 'center',
            gap: '0.5rem'
          }}>
            <Send size={16} />
            Publier le commentaire
          </button>
        </form>
      )}

      <div className="comments-list">
        {comments.length === 0 ? (
          <p style={{ textAlign: 'center', color: '#666', padding: '2rem' }}>
            Aucun commentaire pour le moment. Soyez le premier à donner votre avis !
          </p>
        ) : (
          comments.map((comment) => (
            <CommentItem
              key={comment.id_c}
              comment={comment}
              currentUser={user}
              onEdit={handleEditComment}
              onDelete={handleDeleteComment}
              renderStars={renderStars}
              editingComment={editingComment}
              setEditingComment={setEditingComment}
            />
          ))
        )}
      </div>
    </div>
  );
};

const CommentItem = ({ 
  comment, 
  currentUser, 
  onEdit, 
  onDelete, 
  renderStars, 
  editingComment, 
  setEditingComment 
}) => {
  const [editContent, setEditContent] = useState(comment.contenu);
  const [editRating, setEditRating] = useState(comment.note);

  const isOwner = currentUser && currentUser.id === comment.id_u;
  const isEditing = editingComment === comment.id_c;

  const handleSaveEdit = () => {
    onEdit(comment.id_c, editContent, editRating);
  };

  const handleCancelEdit = () => {
    setEditingComment(null);
    setEditContent(comment.contenu);
    setEditRating(comment.note);
  };

  return (
    <div className="comment-item" style={{
      backgroundColor: 'white',
      border: '1px solid #e0e0e0',
      borderRadius: '8px',
      padding: '1.5rem',
      marginBottom: '1rem'
    }}>
      <div className="comment-header" style={{
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: '0.75rem'
      }}>
        <div>
          <strong>{comment.pseudoUtilisateur}</strong>
          <div style={{ marginTop: '0.25rem' }}>
            {renderStars(isEditing ? editRating : comment.note, isEditing, setEditRating)}
          </div>
        </div>
        
        {isOwner && (
          <div className="comment-actions" style={{ display: 'flex', gap: '0.5rem' }}>
            {isEditing ? (
              <>
                <button onClick={handleSaveEdit} style={{
                  backgroundColor: '#28a745',
                  color: 'white',
                  border: 'none',
                  padding: '0.5rem',
                  borderRadius: '4px',
                  cursor: 'pointer'
                }}>
                  Sauvegarder
                </button>
                <button onClick={handleCancelEdit} style={{
                  backgroundColor: '#6c757d',
                  color: 'white',
                  border: 'none',
                  padding: '0.5rem',
                  borderRadius: '4px',
                  cursor: 'pointer'
                }}>
                  Annuler
                </button>
              </>
            ) : (
              <>
                <button onClick={() => setEditingComment(comment.id_c)} style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  cursor: 'pointer',
                  color: '#0078d7'
                }}>
                  <Edit2 size={16} />
                </button>
                <button onClick={() => onDelete(comment.id_c)} style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  cursor: 'pointer',
                  color: '#dc3545'
                }}>
                  <Trash2 size={16} />
                </button>
              </>
            )}
          </div>
        )}
      </div>

      <div className="comment-content">
        {isEditing ? (
          <textarea
            value={editContent}
            onChange={(e) => setEditContent(e.target.value)}
            style={{
              width: '100%',
              padding: '0.75rem',
              borderRadius: '4px',
              border: '1px solid #ddd',
              resize: 'vertical',
              minHeight: '100px'
            }}
          />
        ) : (
          <p style={{ margin: 0, lineHeight: 1.5 }}>{comment.contenu}</p>
        )}
      </div>
    </div>
  );
};

export default CommentsSection;