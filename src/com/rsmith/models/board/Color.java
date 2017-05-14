package com.rsmith.models.board;

public enum Color {
    WHITE("WHITE"), BLACK("BLACK");

    private final String color;

    private Color(final String color) {
	this.color = color;
    }

    @Override
    public String toString() {
	return color;
    }
}
