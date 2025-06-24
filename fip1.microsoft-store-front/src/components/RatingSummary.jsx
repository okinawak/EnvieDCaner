import React, { useState, useEffect } from 'react';
import StarRating from './StarRating';

const RatingSummary = ({ gameId, compact = false }) => {
  const [ratingData, setRatingData] = useState({
    averageRating: 0,
    commentCount: 0,
    loading: true
  });

  useEffect(() => {
    if (!gameId) return;

    const fetchRatingData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/commentaires/application/${gameId}`);
        if (response.ok) {
          const comments = await response.json();
          const averageRating = comments.length > 0 
            ? (comments.reduce((sum, comment) => sum + comment.note, 0) / comments.length)
            : 0;
          
          setRatingData({
            averageRating: averageRating,
            commentCount: comments.length,
            loading: false
          });
        } else {
          setRatingData(prev => ({ ...prev, loading: false }));
        }
      } catch (error) {
        console.error('Erreur lors du chargement des notes:', error);
        setRatingData(prev => ({ ...prev, loading: false }));
      }
    };

    fetchRatingData();
  }, [gameId]);

  if (ratingData.loading) {
    return compact ? null : <div>Chargement...</div>;
  }

  if (ratingData.commentCount === 0) {
    return compact ? (
      <span style={{ color: '#999', fontSize: '0.8rem' }}>Pas encore d'avis</span>
    ) : (
      <div style={{ color: '#666', fontStyle: 'italic' }}>Aucun avis pour le moment</div>
    );
  }

  return (
    <div className={`rating-summary ${compact ? 'compact' : ''}`} style={{
      display: 'flex',
      alignItems: 'center',
      gap: compact ? '0.25rem' : '0.5rem'
    }}>
      <StarRating 
        rating={Math.round(ratingData.averageRating)} 
        size={compact ? 12 : 16}
      />
      <span style={{ 
        fontSize: compact ? '0.8rem' : '0.9rem',
        color: '#666'
      }}>
        {ratingData.averageRating.toFixed(1)} ({ratingData.commentCount} avis)
      </span>
    </div>
  );
};

export default RatingSummary;