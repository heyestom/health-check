(ns health-check.views.nav
  (:require [health-check.subs :as subs]
            [re-frame.core :as re-frame]))

(defn- nav-item [href label]
  [:li.pure-menu-item.nav-item
   [:a.pure-menu-link {:href href} label]])

(defn navigation-bar []
  [:div.nav-bar.pure-menu.pure-menu-horizontal
   [:ul.pure-menu-list
    [nav-item "#/" "Monitor"]
    [nav-item "#/settings" "Settings"]
    [nav-item "#/about" "About"]]])
