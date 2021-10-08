package com.openclassrooms.entrevoisins.service;

import android.widget.Toast;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() { return neighbours; }

    

    public List<Neighbour> getFavoriteNeighbours(){
        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        for (int i=0; i < neighbours.size(); i++){

            if(neighbours.get(i).isFavorite()){
               favoriteNeighbours.add(neighbours.get(i));
            }
        }
        return favoriteNeighbours;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) { neighbours.remove(neighbour); }


    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) { neighbours.add(neighbour); }
}
