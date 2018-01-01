(ns health-check.views.about-page
  (:require [health-check.views.nav :as nav]))

;; about
(defn about-panel []
  [:div
   [nav/navigation-bar]
   [:div "This is the About Page."
    [:div [:a {:href "#/"} "go to Home Page"]]]])
