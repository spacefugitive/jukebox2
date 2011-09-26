(ns jukebox-web.views.playlist
  (:require [jukebox-web.views.layout :as layout])
  (:use [hiccup core page-helpers]
        [hiccup core form-helpers]
        [jukebox-player.tags]))

(defn display-song [song request]
  (let [tags (extract-tags song)]
    [:div.song.media-grid
      [:div.album-cover {:data-thumbnail "large" :data-artist (:artist tags) :data-album (:album tags)}]
      [:div.meta-data
        [:h1.title (:title tags)]
        [:p.artist (:artist tags)]
        [:p.album (:album tags)]
        [:p.controls
         [:a.btn {:href "/player/play" :data-remote "true"} "Play"]
         [:a.btn {:href "/player/pause" :data-remote "true"} "Pause"]
         (when-not (nil? (-> request :session :current-user)) [:a.btn {:href "/player/skip" :data-remote "true"} "Skip"])]]]))

(defn playlist [song]
  (let [tags (extract-tags song)]
    [:div.meta-data
     [:h6.title (:title tags)]
     [:p.artist (:artist tags)]]))

(defn index [request current-song queued-songs]
  (layout/main request "Playlist"
     (display-song current-song request)
     [:div.row
       [:h3 "Playlist"]
       (if-not (empty? queued-songs)
         [:ol#playlist.span6.clearfix (map #(vector :li (playlist %)) queued-songs)]
         [:p.random "Choosing random tracks"])]))
