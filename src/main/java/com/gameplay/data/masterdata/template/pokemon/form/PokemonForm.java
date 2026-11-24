package com.gameplay.data.masterdata.template.pokemon.form;

import com.gameplay.data.masterdata.attribute.pokemon.appearance.BodyColor;
import com.gameplay.data.masterdata.attribute.pokemon.appearance.BodyShape;
import com.gameplay.data.masterdata.attribute.pokemon.breeding.EggGroup;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.data.masterdata.template.pokemon.form.gigantamax.GigantamaxForm;
import com.gameplay.data.masterdata.template.pokemon.form.mega.MegaEvolutionForm;
import com.utils.StringUtils;

import java.util.*;

public class PokemonForm {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int pokedexNumber;
	private final String speciesCode;
	private final int pokemonFormId;
	private final String pokemonFormCode;
	private final String pokemonCategory;
	private final List<PokemonType> pokemonTypeList;
	private final Set<EggGroup> eggGroups;
	private final BodyColor color;
	private final BodyShape shape;
	private final double height;
	private final double weight;
	private final boolean genderAppearanceDifference;
		/*--------------------------------------------------------------------------------------------------------------
		a.	Mega Evolution																								*/
		private final MegaEvolutionForm megaEvolutionForm;
		/*--------------------------------------------------------------------------------------------------------------
		b.	Gigantamax																									*/
		private final GigantamaxForm gigantamaxForm;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PokemonForm(Builder builder) {
		this.pokedexNumber = builder.pokedexNumber;
		this.speciesCode = builder.speciesCode;
		this.pokemonFormId = builder.pokemonFormId;
		this.pokemonFormCode = builder.pokemonFormCode;
		this.pokemonCategory = builder.pokemonCategory;
		this.pokemonTypeList = List.copyOf(builder.pokemonTypeList);
		this.eggGroups = Collections.unmodifiableSet(EnumSet.copyOf(builder.eggGroups));
		this.color = builder.color;
		this.shape = builder.shape;
		this.height = builder.height;
		this.weight = builder.weight;
		this.genderAppearanceDifference = builder.genderAppearanceDifference;
		this.megaEvolutionForm = builder.megaEvolutionForm;
		this.gigantamaxForm = builder.gigantamaxForm;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getPokedexNumber() {
		return pokedexNumber;
	}

	public PokemonSpecies getPokemonSpecies() {
		return PokemonRegistry.getSpeciesByPokedexNumber(pokedexNumber);
	}

	public int getPokemonFormId() {
		return pokemonFormId;
	}

	public String getPokemonFormCode() {
		return pokemonFormCode;
	}

	public String getPokemonFormFullCode() {
		if (pokemonFormCode.equalsIgnoreCase("BASE")) {
			return speciesCode;
		}
		return speciesCode + "_" + pokemonFormCode;
	}

	public String getPokemonCategory() {
		return pokemonCategory;
	}

	public List<PokemonType> getTypeList() {
		return pokemonTypeList;
	}

	public PokemonType getPrimaryType() {
		return pokemonTypeList.getFirst();
	}

	public PokemonType getSecondaryType() {
		return (pokemonTypeList.size() == 1) ? PokemonType.TYPELESS : pokemonTypeList.getLast();
	}

	public Set<EggGroup> getEggGroups() {
		return eggGroups;
	}

	public boolean hasEggGroup(EggGroup eggGroup) {
		return eggGroups.contains(eggGroup);
	}

	public List<EggGroup> getEggGroupList() {
		return eggGroups.stream().toList();
	}

	public BodyColor getColor() {
		return color;
	}

	public BodyShape getShape() {
		return shape;
	}

	public double getHeight() {
		return height;
	}

	public double getWeight() {
		return weight;
	}

	public boolean hasGenderAppearanceDifference() {
		return genderAppearanceDifference;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Mega Evolution																									*/

	public boolean isMegaEvolved() {
		return megaEvolutionForm != null;
	}

	public MegaEvolutionForm getMegaEvolutionForm() {
		return megaEvolutionForm;
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Gigantamax																										*/

	public boolean isGigantamaxForm() {
		return gigantamaxForm != null;
	}

	public GigantamaxForm getGigantamaxForm() {
		return gigantamaxForm;
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Builder																											*/

	public static class Builder {
		private final int pokedexNumber;
		private final String speciesCode;
		private final int pokemonFormId;
		private final String pokemonFormCode;
		private String pokemonCategory;
		private final List<PokemonType> pokemonTypeList = new ArrayList<>();
		private final EnumSet<EggGroup> eggGroups = EnumSet.noneOf(EggGroup.class);
		private BodyColor color;
		private BodyShape shape;
		private double height;
		private double weight;
		private boolean genderAppearanceDifference;
		private MegaEvolutionForm megaEvolutionForm;
		private GigantamaxForm gigantamaxForm;

		public Builder(int pokedexNumber, String speciesCode, int pokemonFormId, String pokemonFormCode) {
			this.pokedexNumber = pokedexNumber;
			this.speciesCode = speciesCode;
			this.pokemonFormId = pokemonFormId;
			this.pokemonFormCode = StringUtils.requireUpperSnakeCase(pokemonFormCode, "Pokemon Form Code");
		}

		public Builder setCategory(String pokemonCategory) {
			this.pokemonCategory = StringUtils.upperSnakeCase(pokemonCategory);
			return this;
		}

		public Builder setType(List<String> pokemonTypeList) {
			this.pokemonTypeList.clear();
			if (pokemonTypeList != null) {
				if (pokemonTypeList.isEmpty() || pokemonTypeList.size() > 2) {
					throw new IllegalArgumentException("Number of Types is out of range.");
				}

				for (String pokemonType : pokemonTypeList) {
					PokemonType type = PokemonType.fromCode(pokemonType);
					if (type != null && !this.pokemonTypeList.contains(type)) {
						this.pokemonTypeList.add(type);
					}
				}
			}
			return this;
		}

		public Builder setEggGroups(List<String> eggGroupList) {
			this.eggGroups.clear();
			if (eggGroupList != null) {
				if (eggGroupList.isEmpty() || eggGroupList.size() > 2) {
					throw new IllegalArgumentException("Number of Egg Groups is out of range.");
				}

				for (String eggGroupCode : eggGroupList) {
					EggGroup eggGroup = EggGroup.fromCode(eggGroupCode);
					if (eggGroup != null) {
						this.eggGroups.add(eggGroup);
					}
				}
			}
			return this;
		}

		public Builder setAppearance(String color, String shape, boolean genderAppearanceDifference) {
			BodyColor bodyColor = BodyColor.fromCode(color);
			BodyShape bodyShape = BodyShape.fromCode(shape);

			if (bodyColor != null && bodyShape != null) {
				this.color = bodyColor;
				this.shape = bodyShape;
				this.genderAppearanceDifference = genderAppearanceDifference;
			}

			return this;
		}

		public Builder setDimension(double height, double weight) {
			this.height = height;
			this.weight = weight;
			return this;
		}

		public void setMegaEvolution(MegaEvolutionForm megaEvolution) {
			if (megaEvolution != null) {
				this.megaEvolutionForm = megaEvolution;
			}
		}

		public void setGigantamax(GigantamaxForm gigantamax) {
			if (gigantamax != null) {
				this.gigantamaxForm = gigantamax;
			}
		}

		public PokemonForm build() {
			return new PokemonForm(this);
		}
	}
}
